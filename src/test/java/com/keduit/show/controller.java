package com.keduit.show;

import com.keduit.show.dto.BoardDTO;
import com.keduit.show.dto.MemberDTO;
import com.keduit.show.entity.Member;
import com.keduit.show.service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Random;

@SpringBootTest
public class controller {

    @Autowired
    MemberService memberService;
    @Autowired
    BoardService boardService;
    @Autowired
    FavoriteService favoriteService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    ReplyService replyService;
    @Autowired
    OrderService orderService;
    @Autowired
    PasswordEncoder passwordEncoder;

    String[] userIds = {
            "kimminsu001", "parkjihun123", "leejunseo456", "choihyunwoo789", "jungejin987",
            "youjaeseok654", "hanjimin321", "shinwoobin222", "oyoori111", "limseohyun555",
            "seoeunseo333", "baekseungwoo888", "kangyerin999", "moonseoyun000", "hongjiho777",
            "kwonminjae444", "yunseojoon666", "ansumin555", "nojungmin111", "leeseoyoung222",
            "hwangjihun333", "choyounghoon999", "simyeeun000", "junghajoon888", "limdahyun123",
            "hanjiho456", "songminseo654", "heoenchae321", "youseojin987", "goyunah654",
            "jeonminjoon777", "choidaeun987", "ryujunyoung555", "kangjiho444", "jungsooa222",
            "sonjihun111", "joohayeun000", "yangjunhyuk999", "chayejin888", "jungseoyun333",
            "yoonayun123", "kimdahee456", "parkjungwoo654", "hwangseoyun321", "gohajoon987",
            "limminseo444", "joyoojin555", "baesuhyun111", "baekjiho888", "songeyeeun000"
    };

    @Test
    @DisplayName("회원 더미데이터 삽입")
    public void createMember() {
        String[] koreanNames = {
                "김민수", "박지훈", "이준서", "최현우", "정예진",
                "유재석", "한지민", "신우빈", "오유리", "임서현",
                "서은서", "백승우", "강예린", "문서윤", "홍지호",
                "권민재", "윤서준", "안수민", "노정민", "이서영",
                "황지민", "조영훈", "심예은", "정하준", "임다현",
                "한지호", "송민서", "허은채", "유서진", "고윤아",
                "전민준", "최다은", "류준영", "강지호", "정수아",
                "손지훈", "주하은", "양준혁", "차예진", "정서윤",
                "유나연", "김다희", "박정우", "황서윤", "고하준",
                "임민서", "조유진", "배수현", "백지호", "송예은"
        };
        String[] phoneNumbers = {
                "01012345678", "01023456789", "01034567890", "01045678901", "01056789012",
                "01067890123", "01078901234", "01089012345", "01090123456", "01001234567",
                "01011223344", "01022334455", "01033445566", "01044556677", "01055667788",
                "01066778899", "01077889900", "01088990011", "01099001122", "01010101010",
                "01011112222", "01022223333", "01033334444", "01044445555", "01055556666",
                "01066667777", "01077778888", "01088889999", "01099990000", "01001010101",
                "01011110000", "01022220000", "01033330000", "01044440000", "01055550000",
                "01066660000", "01077770000", "01088880000", "01099990000", "01000001111",
                "01011112233", "01022223344", "01033334455", "01044445566", "01055556677",
                "01066667788", "01077778899", "01088889900", "01099990011", "01001010101"
        };
        String[] emails = {
                "kimminsu@naver.com", "parkjihun@gmail.com", "leejunseo@daum.net", "choihyunwoo@naver.com", "jungejin@gmail.com",
                "youjaeseok@daum.net", "hanjimin@naver.com", "shinwoobin@gmail.com", "oyoori@daum.net", "limseohyun@naver.com",
                "seoeunseo@gmail.com", "baekseungwoo@daum.net", "kangyerin@naver.com", "moonseoyun@gmail.com", "hongjiho@daum.net",
                "kwonminjae@naver.com", "yunseojoon@gmail.com", "ansumin@daum.net", "nojungmin@naver.com", "leeseoyoung@gmail.com",
                "hwangjihun@daum.net", "choyounghoon@naver.com", "simyeeun@gmail.com", "junghajoon@daum.net", "limdahyun@naver.com",
                "hanjiho@gmail.com", "songminseo@daum.net", "heoenchae@naver.com", "youseojin@gmail.com", "goyunah@daum.net",
                "jeonminjoon@naver.com", "choidaeun@gmail.com", "ryujunyoung@daum.net", "kangjiho@naver.com", "jungsooa@gmail.com",
                "sonjihun@daum.net", "joohayeun@naver.com", "yangjunhyuk@gmail.com", "chayejin@daum.net", "jungseoyun@naver.com",
                "yoonayun@gmail.com", "kimdahee@daum.net", "parkjungwoo@naver.com", "hwangseoyun@gmail.com", "gohajoon@daum.net",
                "limminseo@naver.com", "joyoojin@gmail.com", "baesuhyun@daum.net", "baekjiho@naver.com", "songeyeeun@gmail.com"
        };
        MemberDTO memberDTO = new MemberDTO();
        for (int i = 0; i < 50; i++) {
            memberDTO.setName(koreanNames[i]);
            memberDTO.setId(userIds[i]);
            memberDTO.setPassword("1234");
            memberDTO.setPhone(phoneNumbers[i]);
            memberDTO.setEmail(emails[i]);

            memberService.saveMember(Member.createMember(memberDTO, passwordEncoder));
        }
    }

    @Test
    @DisplayName("게시글 더미데이터 삽입")
    public void createBoards() {
        String[] boardTitles = {
                "이번 주말에 볼만한 공연 추천", "뮤지컬 티켓 예매 팁 공유", "다음 달 뮤지컬 일정이 궁금해요", "어제 본 공연 후기 남깁니다",
                "가족과 함께 볼 수 있는 공연 추천 부탁드려요", "공연 예매 사이트 비교해봤어요", "인생 최고의 뮤지컬을 만났습니다",
                "이번 달 공연 중 최고의 작품은?", "뮤지컬 초보자가 봐야 할 추천작", "첫 뮤지컬 관람, 너무 감동했어요",
                "연극과 뮤지컬의 차이점이 뭔가요?", "친구들과 함께 갈 만한 공연 추천", "공연 티켓 예매 실패 경험담",
                "무대 앞자리와 뒷자리의 차이점", "자주 가는 공연장 위치 추천", "공연 관람 중 가장 인상 깊었던 장면",
                "뮤지컬 넘버 중 최고는 무엇일까요?", "공연 후 팬미팅 참석 후기", "제일 기억에 남는 배우는 누구인가요?",
                "공연 복장 팁, 무엇을 입어야 할까요?", "다음 달 예매 오픈 소식 기다리고 있어요", "공연 중간에 퇴장해도 될까요?",
                "좋은 공연 자리를 예매하는 방법", "친구 생일 선물로 공연 티켓을 주려는데 추천 부탁드려요", "공연 중 몰입도가 중요한 이유",
                "이색적인 뮤지컬 추천 부탁드립니다", "공연장 좌석 선택의 중요성", "이번 시즌 꼭 봐야 하는 공연",
                "공연장에서 있었던 재미있는 에피소드", "가족과 함께 본 공연 후기", "티켓 양도가 가능한가요?",
                "공연 할인 받는 꿀팁 알려드려요", "해외 공연도 예매할 수 있나요?", "공연 예매 시 주의할 점",
                "여름 공연장에서 시원하게 즐기는 법", "어린이들과 함께 볼 수 있는 공연 추천", "공연 후 뒷풀이 장소 추천",
                "마지막 공연일에 서프라이즈가 있었다고?", "공연 전 꼭 챙겨야 할 물품들", "좋은 공연을 위한 기본 매너",
                "공연 중 핸드폰 사용에 대한 의견", "올해 꼭 봐야 하는 뮤지컬 리스트", "기립박수를 언제 해야 하나요?",
                "공연장의 분위기 따라 다른 재미", "공연 예매 시 자주 묻는 질문", "이 공연을 절대 놓치지 마세요!",
                "주말 공연 추천 부탁드립니다", "학생 할인 받을 수 있는 공연들", "좌석 등급에 따른 시야 차이",
                "공연장을 처음 가보는 사람들에게", "공연 중 촬영에 대한 규정이 있나요?", "배우와 팬의 소통 방법",
                "이번 시즌 최고의 무대 효과는?", "공연 후 사인회에 대한 경험", "연극과 뮤지컬을 모두 좋아하는 사람들",
                "여름 공연장에 시원하게 가는 법", "가족과 함께 보기 좋은 뮤지컬", "최고의 공연 관람 장소는 어디?",
                "뮤지컬 중 최고의 감동 장면은?", "아이와 함께 가는 공연, 어디가 좋을까요?", "공연 티켓 오픈 알림 방법",
                "내 인생 최고의 뮤지컬 넘버", "공연 관람 전 준비할 것들", "티켓을 싸게 사는 법",
                "가장 기대되는 신작 뮤지컬", "해외 공연 예매가 가능한가요?", "공연장 근처 맛집 추천",
                "공연 후 Q&A 시간에서 나온 질문들", "공연을 취소했을 때 환불 절차", "티켓팅 실패 경험 나눠요",
                "공연장 분위기와 매너", "최고의 연기력 가진 배우는?", "뮤지컬 음악 CD 구매처 추천",
                "공연 시작 전에 해야 할 일", "친구와 함께 가기 좋은 공연", "티켓 오픈 소식 공유합니다",
                "첫 공연 관람 전 설렘", "공연 후 남는 여운을 극복하는 법", "배우와 관객의 소통 방식",
                "공연장을 더 재미있게 즐기는 법", "공연장 분위기를 망치는 요소들", "최근 본 뮤지컬 추천드립니다",
                "뮤지컬 의상과 무대 디자인 분석", "공연 좌석별 관람 후기를 남깁니다", "공연을 추천할 때 중요한 요소는?",
                "공연 관련 굿즈 구매처 공유", "뮤지컬 신작 소식 공유합니다", "공연 전 꼭 챙겨야 할 정보",
                "아이들과 가기 좋은 공연", "제일 좋아하는 배우와 작품은?", "공연장 가는 길이 궁금해요",
                "뮤지컬 넘버를 직접 들어볼 수 있는 곳", "공연 중 감동적인 장면 공유", "공연에서 놓칠 수 없는 명장면은?",
                "공연 후기를 남겨봅니다", "공연 티켓 예매 전 꼭 알아둘 점"
        };
        String[] boardContents = {
                "이번 주말에 볼만한 공연 추천 좀 해주세요! 가족과 함께 가려고 하는데 어떤 공연이 좋을까요?",
                "뮤지컬 티켓 예매할 때 좀 더 싸게 사는 방법이 있을까요? 예매 팁 좀 공유 부탁드립니다.",
                "다음 달 뮤지컬 일정이 어떻게 되는지 궁금합니다. 특히 인기 있는 작품이 궁금해요!",
                "어제 본 공연이 너무 감동적이었어요. 무대 연출도 최고였고 배우들 연기도 훌륭했습니다.",
                "가족과 함께 볼 수 있는 공연 추천 부탁드립니다. 모두가 즐길 수 있는 공연을 찾고 있어요.",
                "여러 예매 사이트 중에서 어디가 가장 좋은지 비교해봤습니다. 참고하세요!",
                "인생 최고의 뮤지컬을 만나 감동했습니다. 눈물까지 흘렸어요. 꼭 한 번 보세요!",
                "이번 달 공연 중 가장 인상 깊었던 작품은 무엇인가요? 추천 부탁드려요.",
                "뮤지컬을 처음 접하는 초보자에게 추천할 만한 작품이 있나요? 좀 쉽게 볼 수 있는 걸로요!",
                "첫 뮤지컬 관람을 했는데 너무 감동적이었어요. 또 가고 싶네요!",
                "연극과 뮤지컬의 차이점이 뭘까요? 연극도 재미있게 봤는데, 뮤지컬은 어떨지 궁금하네요.",
                "친구들이랑 같이 볼 수 있는 공연을 추천해주세요! 재미있게 볼 수 있는 작품이었으면 좋겠어요.",
                "공연 티켓을 예매하는 데 실패했어요. 다음엔 어떻게 하면 예매에 성공할 수 있을까요?",
                "무대 앞자리와 뒷자리는 어떻게 다른가요? 시야가 차이가 많이 날까요?",
                "제가 자주 가는 공연장 위치 추천드릴게요. 교통이 편리한 곳이라 자주 가요.",
                "공연 관람 중 가장 인상 깊었던 장면을 공유하고 싶어요. 너무 멋진 무대였어요.",
                "뮤지컬 넘버 중에서 어떤 곡이 가장 좋으세요? 저는 이번 작품의 마지막 곡이 최고였어요.",
                "공연 후에 팬미팅에 참여했어요. 배우들과 가까이서 소통할 수 있어서 너무 좋았습니다.",
                "제일 기억에 남는 배우는 누구인가요? 저는 이번에 본 배우의 연기가 너무 좋았어요.",
                "공연 볼 때 어떤 복장이 좋을까요? 편하게 입고 가도 될까요?",
                "다음 달 예매 오픈 소식 아시는 분 있나요? 특정 공연 기다리고 있는데 알려주세요!",
                "공연 중간에 퇴장해야 할 상황이 생기면 어떻게 해야 할까요? 중간에 나가도 괜찮을지 궁금해요.",
                "좋은 자리를 예매하려면 어떤 방법이 좋을까요? 항상 예매 실패해서 좋은 자리 못 잡아요.",
                "친구 생일에 공연 티켓을 선물로 주고 싶은데, 어떤 공연이 좋을지 추천 부탁드립니다.",
                "공연 중 몰입도가 정말 중요하다고 생각해요. 배우들의 감정이 그대로 전달되는 느낌이었어요.",
                "이색적인 뮤지컬을 추천받고 싶어요. 색다른 경험을 할 수 있는 공연 없을까요?",
                "공연장에서 좌석 선택이 정말 중요한 것 같아요. 어디 앉느냐에 따라 분위기가 달라지더라고요.",
                "이번 시즌 꼭 봐야 하는 공연이 있다면 추천 부탁드려요. 어떤 작품이 인기가 많은지 궁금해요.",
                "공연장에서 있었던 재미있는 에피소드를 나누고 싶어요. 다들 이런 경험 있으신가요?",
                "가족들과 함께 본 공연 너무 좋았어요. 가족 모두가 즐겁게 볼 수 있는 공연이었습니다.",
                "공연 티켓을 양도받을 수 있나요? 예매 못해서 양도 구해보려고 하는데요.",
                "공연 티켓을 할인받는 팁 좀 알려드릴게요. 조금이나마 도움이 되셨으면 좋겠어요.",
                "해외 공연도 예매가 가능하다는 걸 알고 계셨나요? 좋은 정보가 될 것 같아서 공유해요.",
                "공연 예매할 때 주의해야 할 점이 있을까요? 예매 초보라서 궁금해요.",
                "여름에 시원하게 공연을 즐길 수 있는 방법이 있을까요? 팁 좀 주세요!",
                "아이들과 함께 볼 수 있는 공연을 추천해주실 수 있나요? 아이가 좋아할 만한 걸로요.",
                "공연 끝나고 뒷풀이 장소를 찾고 있는데, 근처에 좋은 곳 있으면 추천 부탁드려요.",
                "마지막 공연일에 무대에서 서프라이즈 이벤트가 있었는데, 너무 감동적이었어요!",
                "공연 전 준비물 중에 꼭 챙겨야 할 것이 있나요? 공연 처음 가는데 도움이 필요해요.",
                "좋은 공연 관람을 위해 지켜야 할 기본 매너가 있을까요? 다들 알고 가셨으면 좋겠네요.",
                "공연 중 핸드폰 사용하는 사람들 때문에 불편했는데, 여러분 생각은 어떠신가요?",
                "올해는 정말 많은 뮤지컬이 열리네요. 꼭 봐야 할 공연 리스트를 공유합니다.",
                "기립박수는 언제 해야 하나요? 타이밍을 잘 모르겠어서 물어봐요.",
                "공연장의 분위기에 따라 재미가 달라지더라구요. 공연은 어디서 보는지가 중요한 것 같아요.",
                "공연 예매할 때 자주 묻는 질문들 정리해봤어요. 참고하세요!",
                "이 공연은 절대 놓치지 마세요! 너무 감동적이었어요.",
                "주말에 볼만한 공연을 추천해주실 수 있나요? 시간이 있어서 가려고 합니다.",
                "학생 할인이 가능한 공연들 목록을 찾고 있어요. 정보 공유 부탁드려요.",
                "좌석 등급에 따라 시야 차이가 많이 나는지 궁금해요. 무대가 잘 보일까요?",
                "공연장에 처음 가는 사람들에게 팁을 줄 수 있을까요? 무엇을 준비해야 할까요?",
                "공연 중 촬영에 대한 규정이 있나요? 사진을 찍고 싶은데 가능한지 궁금합니다.",
                "배우와 팬의 소통 방식이 궁금해요. 팬미팅이나 싸인회에 어떻게 참여하나요?",
                "이번 시즌 최고의 무대 효과는 어떤 작품에서 볼 수 있을까요? 정말 궁금해요.",
                "공연 후 사인회에 참여했는데, 배우들이 너무 친절하게 대해주셔서 기뻤습니다.",
                "연극과 뮤지컬을 둘 다 좋아하는 사람들에게 추천할 만한 작품이 있을까요?",
                "여름 공연장에 시원하게 가는 방법을 아시는 분 계신가요? 팁 좀 주세요.",
                "가족과 함께 볼 수 있는 뮤지컬을 추천해주세요. 가족 모두가 좋아할 만한 작품이면 좋겠어요.",
                "최고의 공연 관람 장소는 어디일까요? 무대가 잘 보이는 자리를 추천해주세요.",
                "뮤지컬 중 가장 감동적인 장면이 무엇인지 나누고 싶어요. 이번 공연은 정말 최고였어요.",
                "아이들과 함께 가기 좋은 공연을 찾고 있어요. 추천해주실 수 있나요?",
                "공연 티켓 오픈 알림을 어떻게 받나요? 좋은 방법이 있으면 알려주세요.",
                "내 인생 최고의 뮤지컬 넘버를 나누고 싶어요. 너무 감동적이었어요.",
                "공연 관람 전 준비해야 할 것들을 미리 챙겨보세요. 도움이 되실 거예요.",
                "티켓을 싸게 사는 방법이 있을까요? 예매할 때마다 비싸서 힘들어요.",
                "가장 기대되는 신작 뮤지컬이 뭐가 있을까요? 기대되는 작품을 추천해주세요.",
                "해외 공연 예매도 가능한지 궁금해요. 방법을 아시는 분 있나요?",
                "공연장 근처에 맛집 추천해주실 수 있나요? 공연 보고 나서 가려고 합니다.",
                "공연 후 Q&A 시간에서 어떤 질문들이 나왔는지 공유해드릴게요.",
                "공연 취소 시 환불 절차는 어떻게 되나요? 갑자기 취소하게 되었는데 궁금합니다.",
                "티켓팅 실패 경험 나눠요. 정말 아쉬웠던 경험이었어요.",
                "공연장 분위기와 매너에 대해 이야기해보고 싶어요. 다들 매너를 지키는 게 중요한 것 같아요.",
                "최고의 연기력을 가진 배우는 누구라고 생각하세요? 저는 이번 작품에서 정말 인상 깊었습니다.",
                "뮤지컬 음악 CD를 구매할 수 있는 곳 추천 좀 부탁드릴게요. 너무 좋아서 소장하고 싶어요.",
                "공연 시작 전에 해야 할 일이 있다면 뭐가 있을까요? 준비물 챙기는 것도 중요한 것 같아요.",
                "친구와 함께 가기 좋은 공연을 추천해주실 수 있나요? 함께 즐길 수 있는 작품이면 좋겠어요.",
                "티켓 오픈 소식을 공유합니다. 예매 성공을 기원합니다!",
                "첫 공연 관람 전의 설렘을 나누고 싶어요. 기대가 많이 돼요.",
                "공연 후 남는 여운을 어떻게 극복하시나요? 여운이 너무 오래 가요.",
                "배우와 관객의 소통이 중요한 것 같아요. 그 소통 덕분에 공연이 더 빛나는 것 같습니다."
        };

        BoardDTO boardDTO = new BoardDTO();
        List<Member> members = memberService.findAllMembers();  // 모든 회원 가져오기
        Random random = new Random();

//        for (int i = 0; i < 100; i++) {
            Member randomMember = members.get(random.nextInt(members.size()));
            boardDTO.setTitle(boardTitles[0]);
            boardDTO.setContent(boardContents[0]);
            boardDTO.setCreateBy(randomMember.getId());
            System.out.println(boardDTO + "=================================================boardDTo");

            boardService.saveBoard(boardDTO, randomMember.getId());
            System.out.println(randomMember.getId() + "=================================================getId");
//        }
    }




}
