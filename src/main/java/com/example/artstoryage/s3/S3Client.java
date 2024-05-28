package com.example.artstoryage.s3;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.artstoryage.config.AwsConfig;
import com.example.artstoryage.exception.GlobalErrorCode;
import com.example.artstoryage.exception.custom.S3Exception;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3Client {

  private final AmazonS3 amazonS3;
  private final AwsConfig awsConfig;
  private String S3_URL_PATTERN;

  private static final Set<String> IMAGE_TYPES = new HashSet<>(Arrays.asList("jpg", "jpeg", "png"));
  private static final Set<String> ARTWORK_TYPES =
      new HashSet<>(Arrays.asList("jpg", "jpeg", "hwp", "docx"));
  private static final int MAX_POST_IMAGES = 10;

  @PostConstruct
  private void init() {
    S3_URL_PATTERN =
        "https://"
            + awsConfig.getBucket()
            + "\\.s3\\."
            + awsConfig.getRegion()
            + "\\.amazonaws\\.com(.*)";
  }

  public String uploadFile(MultipartFile file, String directory) throws S3Exception {
    if (file.isEmpty()) {
      throw new S3Exception(GlobalErrorCode.UPLOAD_NULL_FILE);
    }

    String fileType = file.getOriginalFilename();

    switch (S3Directory.valueOf(directory)) {
      case ARTWORK:
      case BANKBOOK:
      case EXNOTICE:
      case ARTIST:
        if (!IMAGE_TYPES.contains(fileType)) {
          throw new S3Exception(GlobalErrorCode.INVALID_FILE);
        }
        break;
      case EXART:
        if (!ARTWORK_TYPES.contains(fileType)) {
          throw new S3Exception(GlobalErrorCode.INVALID_FILE);
        }
    }
    return uploadFileToS3(
        generateKeyName(S3Directory.valueOf(directory), file.getOriginalFilename()), file);
  }

  public List<String> uploadFiles(List<MultipartFile> files, String directory) throws S3Exception {
    if (files.size() > MAX_POST_IMAGES) {
      throw new S3Exception(GlobalErrorCode.TOO_MANY_FILES);
    }

    List<String> uploadedUrls = new ArrayList<>();
    for (MultipartFile file : files) {

      String fileType = file.getOriginalFilename();

      if (!IMAGE_TYPES.contains(fileType)) {
        throw new S3Exception(GlobalErrorCode.INVALID_FILE);
      }

      String uploadedFileUrl =
          uploadFileToS3(
              generateKeyName(S3Directory.valueOf(directory), file.getOriginalFilename()), file);
      uploadedUrls.add(uploadedFileUrl);
    }
    return uploadedUrls;
  }

  private String uploadFileToS3(String keyName, MultipartFile file) throws S3Exception {
    try {
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(file.getSize());
      amazonS3.putObject(
          new PutObjectRequest(awsConfig.getBucket(), keyName, file.getInputStream(), metadata));

      return amazonS3.getUrl(awsConfig.getBucket(), keyName).toString();
    } catch (SdkClientException e) {
      throw new S3Exception(GlobalErrorCode.S3_ERROR);
    } catch (IOException e) {
      throw new S3Exception(GlobalErrorCode.INVALID_FILE);
    }
  }

  private void deleteFile(String keyName) throws S3Exception {
    try {
      if (amazonS3.doesObjectExist(awsConfig.getBucket(), keyName)) {
        amazonS3.deleteObject(awsConfig.getBucket(), keyName);
      } else {
        throw new S3Exception(GlobalErrorCode.S3_FILE_NOT_FOUND);
      }
    } catch (Exception e) {
      throw new S3Exception(GlobalErrorCode.S3_ERROR);
    }
  }

  private String getKeyName(String fileUrl) {
    Pattern regex = Pattern.compile(S3_URL_PATTERN);
    Matcher matcher = regex.matcher(fileUrl);

    String keyName = null;
    if (matcher.find()) {
      keyName = matcher.group(1).substring(1);
    }

    return keyName;
  }

  private String generateKeyName(S3Directory dir, String filename) {
    return dir.getDirectory() + "/" + UUID.randomUUID() + "_" + filename;
  }
}
