package com.sparta.spring_jwt.service;

import com.sparta.spring_jwt.models.repository.UserRepository;
import com.sparta.spring_jwt.models.user.User;
import com.sparta.spring_jwt.models.userDto.SignupRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;


    @Nested
    @DisplayName("아이디 비밀번호 포맷에 따른 회원가입")
    class RegisterUser {

        private String username;
        private String password;
        private String confirm_password;

        @BeforeEach
        void setup() {
            username = "1245qasd";
            password = "12343";
            confirm_password = "12343";
        }


        @Test
        @DisplayName("정상 케이스")
        void RegisterUser_Normal() {

            SignupRequestDto signupRequestDto = new SignupRequestDto(username, password, confirm_password);


            UserService userService = new UserService(userRepository, passwordEncoder);

            User result = userService.registerUser(signupRequestDto);


            assertEquals(username, result.getUsername());
            assertEquals(passwordEncoder.encode(password), result.getPassword());


        }

        @Nested
        @DisplayName("실패 케이스")
        class failCase {

            @Nested
            @DisplayName("닉네임")
            class fail_nickname {
                @Test
                @DisplayName("3자 미만")
                void nickname_fail1() {
                    username = "12";

                    SignupRequestDto signupRequestDto = new SignupRequestDto(username, password, confirm_password);


                    UserService userService = new UserService(userRepository, passwordEncoder);


                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        userService.registerUser(signupRequestDto);
                    });

                    assertEquals("아이디와 비밀번호를 확인해주세요", exception.getMessage());

                }

                @Test
                @DisplayName("3자 미만2")
                void nickname_fail2() {
                    username = "ad";

                    SignupRequestDto signupRequestDto = new SignupRequestDto(username, password, confirm_password);


                    UserService userService = new UserService(userRepository, passwordEncoder);


                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        userService.registerUser(signupRequestDto);
                    });

                    assertEquals("아이디와 비밀번호를 확인해주세요", exception.getMessage());

                }

                @Test
                @DisplayName("알파벳 대소문자 숫자를 제외한 다른 문자 포함")
                void nickname_fail3(){
                    username = "3321321!@#";

                    SignupRequestDto signupRequestDto = new SignupRequestDto(username,password,confirm_password);


                    UserService userService = new UserService(userRepository,passwordEncoder);


                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        userService.registerUser(signupRequestDto);
                    });

                    assertEquals("아이디와 비밀번호를 확인해주세요", exception.getMessage());

                }

                @Test
                @DisplayName("알파벳 대소문자 숫자를 제외한 다른 문자 포함2")
                void nickname_fail4(){
                    username = "!#@#@";

                    SignupRequestDto signupRequestDto = new SignupRequestDto(username,password,confirm_password);


                    UserService userService = new UserService(userRepository,passwordEncoder);


                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        userService.registerUser(signupRequestDto);
                    });

                    assertEquals("아이디와 비밀번호를 확인해주세요", exception.getMessage());

                }

                @Test
                @DisplayName("중복된 닉네임")
                void nickname_fail5(){
                    String same_username = username;


                    SignupRequestDto signupRequestDto = new SignupRequestDto(username,password,confirm_password);

                    SignupRequestDto anotherSignupRequestDto = new SignupRequestDto(same_username,password,confirm_password);

                    UserService userService = new UserService(userRepository,passwordEncoder);

                    userService.registerUser(signupRequestDto);


                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        userService.registerUser(anotherSignupRequestDto);
                    });


                    assertEquals("중복된 닉네임입니다.", exception.getMessage());


                }


            }

            @Nested
            @DisplayName("비밀번호")
            class fail_password{
                @Test
                @DisplayName("4자 미만")
                void password_fail1() {
                    password = "!23";
                    confirm_password = "!23";

                    SignupRequestDto signupRequestDto = new SignupRequestDto(username, password, confirm_password);


                    UserService userService = new UserService(userRepository, passwordEncoder);


                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        userService.registerUser(signupRequestDto);
                    });

                    assertEquals("아이디와 비밀번호를 확인해주세요", exception.getMessage());

                }

                @Test
                @DisplayName("4자 미만2")
                void password_fail2() {
                    password = "a@c";
                    confirm_password = "a@c";

                    SignupRequestDto signupRequestDto = new SignupRequestDto(username, password, confirm_password);


                    UserService userService = new UserService(userRepository, passwordEncoder);


                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        userService.registerUser(signupRequestDto);
                    });

                    assertEquals("아이디와 비밀번호를 확인해주세요", exception.getMessage());

                }

                @Test
                @DisplayName("닉네임과 같은 값이 포함된 경우")
                void password_fail3() {
                    username = "1234";
                    password = "1234!@#$$#";
                    confirm_password= "1234!@#$$#";

                    SignupRequestDto signupRequestDto = new SignupRequestDto(username, password, confirm_password);


                    UserService userService = new UserService(userRepository, passwordEncoder);


                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        userService.registerUser(signupRequestDto);
                    });

                    assertEquals("아이디와 비밀번호를 확인해주세요", exception.getMessage());

                }

                @Test
                @DisplayName("닉네임과 같은 값이 포함된 경우2")
                void password_fail4() {
                    username = "asA2313";
                    password = "asA2313@!@!@asdS";
                    confirm_password= "asA2313@!@!@asdS";

                    SignupRequestDto signupRequestDto = new SignupRequestDto(username, password, confirm_password);


                    UserService userService = new UserService(userRepository, passwordEncoder);


                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        userService.registerUser(signupRequestDto);
                    });

                    assertEquals("아이디와 비밀번호를 확인해주세요", exception.getMessage());

                }
                @Test
                @DisplayName("비밀번호확인과 비밀번호가 다른 경우")
                void password_fail5() {
                    password = "SDSDSDs321312";
                    confirm_password= "dsds@@$#@#";

                    SignupRequestDto signupRequestDto = new SignupRequestDto(username, password, confirm_password);


                    UserService userService = new UserService(userRepository, passwordEncoder);


                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        userService.registerUser(signupRequestDto);
                    });

                    assertEquals("아이디와 비밀번호를 확인해주세요", exception.getMessage());

                }

                @Test
                @DisplayName("비밀번호확인과 비밀번호가 다른 경우2")
                void password_fail6() {
                    password = "dsadsadsa1234";
                    confirm_password= "dsadsadsa12345";

                    SignupRequestDto signupRequestDto = new SignupRequestDto(username, password, confirm_password);


                    UserService userService = new UserService(userRepository, passwordEncoder);


                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        userService.registerUser(signupRequestDto);
                    });

                    assertEquals("아이디와 비밀번호를 확인해주세요", exception.getMessage());

                }

            }
        }


    }
}



//import com.sparta.springcore.dto.ProductRequestDto;
//        import org.junit.jupiter.api.*;
//
//        import static org.junit.jupiter.api.Assertions.*;
//
//class ProductTest {
//
//    @Nested
//    @DisplayName("회원이 요청한 관심상품 객체 생성")
//    class CreateUserProduct {
//
//        private Long userId;
//        private String title;
//        private String image;
//        private String link;
//        private int lprice;
//
//        @BeforeEach
//        void setup() {
//            userId = 100L;
//            title = "오리온 꼬북칩 초코츄러스맛 160g";
//            image = "https://shopping-phinf.pstatic.net/main_2416122/24161228524.20200915151118.jpg";
//            link = "https://search.shopping.naver.com/gate.nhn?id=24161228524";
//            lprice = 2350;
//        }
//
//        @Test
//        @DisplayName("정상 케이스")
//        void createProduct_Normal() {
//// given
//            ProductRequestDto requestDto = new ProductRequestDto(
//                    title,
//                    image,
//                    link,
//                    lprice
//            );
//
//// when
//            Product product = new Product(requestDto, userId);
//
//// then
//            assertNull(product.getId());
//            assertEquals(userId, product.getUserId());
//            assertEquals(title, product.getTitle());
//            assertEquals(image, product.getImage());
//            assertEquals(link, product.getLink());
//            assertEquals(lprice, product.getLprice());
//            assertEquals(0, product.getMyprice());
//        }
//
//        @Nested
//        @DisplayName("실패 케이스")
//        class FailCases {
//            @Nested
//            @DisplayName("회원 Id")
//            class userId {
//                @Test
//                @DisplayName("null")
//                void fail1() {
//// given
//                    userId = null;
//
//                    ProductRequestDto requestDto = new ProductRequestDto(
//                            title,
//                            image,
//                            link,
//                            lprice
//                    );
//
//// when
//                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//                        new Product(requestDto, userId);
//                    });
//
//// then
//                    assertEquals("회원 Id 가 유효하지 않습니다.", exception.getMessage());
//                }
//
//                @Test
//                @DisplayName("마이너스")
//                void fail2() {
//// given
//                    userId = -100L;
//
//                    ProductRequestDto requestDto = new ProductRequestDto(
//                            title,
//                            image,
//                            link,
//                            lprice
//                    );
//
//// when
//                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//                        new Product(requestDto, userId);
//                    });
//
//// then
//                    assertEquals("회원 Id 가 유효하지 않습니다.", exception.getMessage());
//                }
//            }
//
//            @Nested
//            @DisplayName("상품명")
//            class Title {
//                @Test
//                @DisplayName("null")
//                void fail1() {
//// given
//                    title = null;
//
//                    ProductRequestDto requestDto = new ProductRequestDto(
//                            title,
//                            image,
//                            link,
//                            lprice
//                    );
//
//// when
//                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//                        new Product(requestDto, userId);
//                    });
//
//// then
//                    assertEquals("저장할 수 있는 상품명이 없습니다.", exception.getMessage());
//                }
//
//                @Test
//                @DisplayName("빈 문자열")
//                void fail2() {
//// given
//                    String title = "";
//
//                    ProductRequestDto requestDto = new ProductRequestDto(
//                            title,
//                            image,
//                            link,
//                            lprice
//                    );
//
//// when
//                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//                        new Product(requestDto, userId);
//                    });
//
//// then
//                    assertEquals("저장할 수 있는 상품명이 없습니다.", exception.getMessage());
//                }
//            }
//
//            @Nested
//            @DisplayName("상품 이미지 URL")
//            class Image {
//                @Test
//                @DisplayName("null")
//                void fail1() {
//// given
//                    image = null;
//
//                    ProductRequestDto requestDto = new ProductRequestDto(
//                            title,
//                            image,
//                            link,
//                            lprice
//                    );
//
//// when
//                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//                        new Product(requestDto, userId);
//                    });
//
//// then
//                    assertEquals("상품 이미지 URL 포맷이 맞지 않습니다.", exception.getMessage());
//                }
//
//                @Test
//                @DisplayName("URL 포맷 형태가 맞지 않음")
//                void fail2() {
//// given
//                    image = "shopping-phinf.pstatic.net/main_2416122/24161228524.20200915151118.jpg";
//
//                    ProductRequestDto requestDto = new ProductRequestDto(
//                            title,
//                            image,
//                            link,
//                            lprice
//                    );
//
//// when
//                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//                        new Product(requestDto, userId);
//                    });
//
//// then
//                    assertEquals("상품 이미지 URL 포맷이 맞지 않습니다.", exception.getMessage());
//                }
//            }
//
//            @Nested
//            @DisplayName("상품 최저가 페이지 URL")
//            class Link {
//                @Test
//                @DisplayName("null")
//                void fail1() {
//// given
//                    link = "https";
//
//                    ProductRequestDto requestDto = new ProductRequestDto(
//                            title,
//                            image,
//                            link,
//                            lprice
//                    );
//
//// when
//                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//                        new Product(requestDto, userId);
//                    });
//
//// then
//                    assertEquals("상품 최저가 페이지 URL 포맷이 맞지 않습니다.", exception.getMessage());
//                }
//
//                @Test
//                @DisplayName("URL 포맷 형태가 맞지 않음")
//                void fail2() {
//// given
//                    link = "https";
//
//                    ProductRequestDto requestDto = new ProductRequestDto(
//                            title,
//                            image,
//                            link,
//                            lprice
//                    );
//
//// when
//                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//                        new Product(requestDto, userId);
//                    });
//
//// then
//                    assertEquals("상품 최저가 페이지 URL 포맷이 맞지 않습니다.", exception.getMessage());
//                }
//            }
//
//            @Nested
//            @DisplayName("상품 최저가")
//            class LowPrice {
//                @Test
//                @DisplayName("0")
//                void fail1() {
//// given
//                    lprice = 0;
//
//                    ProductRequestDto requestDto = new ProductRequestDto(
//                            title,
//                            image,
//                            link,
//                            lprice
//                    );
//
//// when
//                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//                        new Product(requestDto, userId);
//                    });
//
//// then
//                    assertEquals("상품 최저가가 0 이하입니다.", exception.getMessage());
//                }
//
//                @Test
//                @DisplayName("음수")
//                void fail2() {
//// given
//                    lprice = -1500;
//
//                    ProductRequestDto requestDto = new ProductRequestDto(
//                            title,
//                            image,
//                            link,
//                            lprice
//                    );
//
//// when
//                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//                        new Product(requestDto, userId);
//                    });
//
//// then
//                    assertEquals("상품 최저가가 0 이하입니다.", exception.getMessage());
//                }
//            }
//        }
//    }
//}