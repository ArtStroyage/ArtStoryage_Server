package com.example.artstoryage.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.artstoryage.domain.Artist;
import com.example.artstoryage.domain.enums.MemberRole;
import com.example.artstoryage.domain.enums.SocialType;
import com.example.artstoryage.domain.member.Member;
import com.example.artstoryage.domain.member.Password;
import com.example.artstoryage.dto.request.ArtistRequestDto.CreateArtistRequest;
import com.example.artstoryage.dto.response.ArtistResponseDto.CreateArtistResponse;
import com.example.artstoryage.service.ArtistCommandService;
import com.example.artstoryage.util.TestConfig;

@WebMvcTest(controllers = ArtistController.class)
@Import(TestConfig.class)
@WithMockUser(username = "name")
public class ArtistControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ArtistCommandService artistCommandService;

  private Member member;
  private Artist artist;
  private CreateArtistResponse createArtistResponse;
  private CreateArtistRequest createArtistRequest;

  @BeforeEach
  public void setup() {

    member =
        Member.builder()
            .name("name")
            .nickName("nickname")
            .email("email")
            .password(Password.encrypt("Test1234!@#$", new BCryptPasswordEncoder()))
            .phoneNumber("010-0000-0000")
            .socialType(SocialType.COMMON)
            .memberRole(MemberRole.USER)
            .build();

    artist =
        Artist.builder()
            .name("name")
            .profileImageLink("link")
            .genre("genre")
            .history("history")
            .introduction("intro")
            .bankBookLink("bank")
            .build();

    createArtistRequest =
        CreateArtistRequest.builder()
            .name("name")
            .profileImageLink("link")
            .genre("genre")
            .introduction("intro")
            .history("history")
            .bankBookLink("bank")
            .build();

    createArtistResponse = CreateArtistResponse.builder().artistId(1L).name("name").build();
  }

  /*
   @Test
   public void createArtist_returns201() throws Exception {

     MockedStatic<ArtistConverter> converter = mockStatic(ArtistConverter.class);

     given(artistCommandService.createArtist(any(Member.class), any(CreateArtistRequest.class)))
         .willReturn(artist);

     given(ArtistConverter.toCreateArtistResponse(any(Artist.class)))
         .willReturn(createArtistResponse);

     RequestBuilder request =
         MockMvcRequestBuilders.post("/api/v1/artist/")
             .content(TestUtil.toJson(createArtistRequest))
             .with(csrf())
             .header("Authorization", TestUtil.createTestToken("name"))
             .contentType(MediaType.APPLICATION_JSON);

     mockMvc
         .perform(request)
         .andExpect(jsonPath("$.artistId").value(createArtistResponse.getArtistId()))
         .andExpect(jsonPath("$.name").value(createArtistResponse.getName()))
         .andDo(print());

     converter.close();
   }

  */
}
