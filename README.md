# 2024-multicampus-final_5-HappyTails
2024 파이널 프로젝트 5조 - Happy Tails

## 🖥️ 팀원 소개
| <img src="https://avatars.githubusercontent.com/Shin-Hyeoncheol" width=90px alt="신현철"/> | <img src="https://avatars.githubusercontent.com/ehdtka" width=90px alt="남동석"/> | <img src="https://avatars.githubusercontent.com/nayoung77" width=90px alt="여나영"/> | <img src="https://avatars.githubusercontent.com/wjh0429" width=90px alt="우재협"/> | <img src="https://avatars.githubusercontent.com/supreme4rest" width=90px alt="이은수"/> |
| :-----: | :-----: | :-----: | :-----: | :-----: |
| [신현철](https://github.com/Shin-Hyeoncheol) | [남동석](https://github.com/ehdtka) | [여나영](https://github.com/nayoung77) | [우재협](https://github.com/wjh0429) | [이은수](https://github.com/supreme4rest) |



## 기술 스택
![Java](https://img.shields.io/badge/Java-007396?style=flat-square&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=javascript&logoColor=black)

## ERD
- [ERD](https://www.erdcloud.com/d/WyztNt6hzckmpBGSK)

## Service Architecture
- Service Architecture 이미지 또는 링크 추가 예정

## 프로젝트 구조
📦HappyTails
 ┣ 📂src
 ┃ ┣ 📂main
 ┃ ┃ ┣ 📂generated
 ┃ ┃ ┣ 📂java
 ┃ ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┃ ┗ 📂multi
 ┃ ┃ ┃ ┃ ┃ ┗ 📂happytails
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂api
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂payment
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ApiKeys.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂authentication
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂model
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜CustomUser.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuthenticationService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜AuthenticationServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂community
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ChatDogController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CommunityAdminController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ConferenceController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜DogloveController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ChatDogDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ConferenceDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜DogloveDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ChatDogDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ConferenceDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜DogloveDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂reply
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ReplyController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ReplyDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ReplyDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ReplyCode.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ReplyService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ChatDogService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ConferenceService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜DogloveService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ContextConfiguration.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ErrorControllerConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜HappytailsApplication.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MybatisConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜RestTemplateConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SecurityConfiguration.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WebSocketConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂DBTI
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜DBTIController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dog4cuts
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜dog4cutsController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜Dog4CutsDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Dog4CutsDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Dog4CutsImgDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜Dog4CutsImgPagingDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜Dog4CutsService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dogNum
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜DogNumController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜DogNumDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜DogNumDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜DogNumService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂finddog
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FindDogAdminController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜FindDogController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜FindDogMapper.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FindDogDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜FindDogReplyDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜FindDogService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂help
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜HelpAdminController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜HelpController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜HelpMapper.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜HelpCategoryDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜InquiryDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜InquiryResultDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PageDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜QuestionDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜HelpService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂honor
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜HonorController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜HonorService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂main
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AdminController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ChatController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CustomErrorController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MainController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜RssNewsDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ClovaService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MainService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MainServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂map
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MapController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂member
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BusinessAdminController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BusinessController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MemberController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BusinessDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MemberDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BusinessDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MemberDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BusinessService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MemberService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂patrol
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PatrolController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PatrolRecordController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PatrolRecordPlaceController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜PatrolReplyController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PatrolDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PatrolPlaceDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PatrolRecordDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜PrecordReplyDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜OnePatrolImgDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜OnePatrolRecordImgDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PatrolDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PatrolImgDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PlacePointListDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PrecordDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PrecordPlaceDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PrecordPlaceHistoryDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜PrecordReplyDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂pageable
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜BoardDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜RequestList.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜PageService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PatrolPlaceService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PatrolRecordReplyService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PatrolRecordService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜PatrolService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂score
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ScoreDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ScoreDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ScoreService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂shop
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CartController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PaymentController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ReviewController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SalesCategoryController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜SalesController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CartDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PaymentDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ReviewDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SalesCategoryDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜SalesGoodsDAO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CartDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜OrderlistDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PartialCancelRequestDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PaymentDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PaymentpageDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ReviewDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SalesCategoryDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SalesGoodsDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜VerificationRequestDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CartService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CartServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PaymentService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ReviewService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ReviewServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SalesCategoryService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SalesCategoryServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SalesService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜SalesServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂upload
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UploadController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UploadMapper.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UploadDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UploadService.java
 ┃ ┃ ┗ 📂resources
 ┃ ┃ ┃ ┣ 📂mappers
 ┃ ┃ ┃ ┃ ┣ 📂community
 ┃ ┃ ┃ ┃ ┃ ┣ 📜chatdogmapper.xml
 ┃ ┃ ┃ ┃ ┃ ┣ 📜conferencemapper.xml
 ┃ ┃ ┃ ┃ ┃ ┣ 📜doglovemapper.xml
 ┃ ┃ ┃ ┃ ┃ ┗ 📜replymapper.xml
 ┃ ┃ ┃ ┃ ┣ 📂dogNum
 ┃ ┃ ┃ ┃ ┃ ┗ 📜dogNummapper.xml
 ┃ ┃ ┃ ┃ ┣ 📂finddog
 ┃ ┃ ┃ ┃ ┃ ┗ 📜FindDogMapper.xml
 ┃ ┃ ┃ ┃ ┣ 📂help
 ┃ ┃ ┃ ┃ ┃ ┗ 📜HelpMapper.xml
 ┃ ┃ ┃ ┃ ┣ 📂member
 ┃ ┃ ┃ ┃ ┃ ┣ 📜BusinessMapper.xml
 ┃ ┃ ┃ ┃ ┃ ┗ 📜MemberMapper.xml
 ┃ ┃ ┃ ┃ ┣ 📂patrol
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Dog4CutsMapper.xml
 ┃ ┃ ┃ ┃ ┃ ┣ 📜pageableMapper.xml
 ┃ ┃ ┃ ┃ ┃ ┣ 📜PatrolMapper.xml
 ┃ ┃ ┃ ┃ ┃ ┣ 📜PatrolPlaceMapper.xml
 ┃ ┃ ┃ ┃ ┃ ┣ 📜PatrolRecordMapper.xml
 ┃ ┃ ┃ ┃ ┃ ┗ 📜PatrolRecordReplyMapper.xml
 ┃ ┃ ┃ ┃ ┣ 📂score
 ┃ ┃ ┃ ┃ ┃ ┗ 📜ScoreMapper.xml
 ┃ ┃ ┃ ┃ ┣ 📂shop
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Cart.xml
 ┃ ┃ ┃ ┃ ┃ ┣ 📜PaymentMapper.xml
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Review.xml
 ┃ ┃ ┃ ┃ ┃ ┣ 📜SalesCategory.xml
 ┃ ┃ ┃ ┃ ┃ ┗ 📜SalesGoods.xml
 ┃ ┃ ┃ ┃ ┗ 📂upload
 ┃ ┃ ┃ ┃ ┃ ┗ 📜UploadMapper.xml
 ┃ ┃ ┃ ┣ 📂static
 ┃ ┃ ┃ ┃ ┣ 📂css
 ┃ ┃ ┃ ┃ ┃ ┣ 📂admin
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜styles.css
 ┃ ┃ ┃ ┃ ┃ ┗ 📂main
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜all.css
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜chat.css
 ┃ ┃ ┃ ┃ ┣ 📂images
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CNEA.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CNEL.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CNIA.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CNIL.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CTEA.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CTEL.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CTIA.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜CTIL.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜dbtiexplain.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜dog.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜personalityType.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜WNEA.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜WNEL.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜WNIA.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜WNIL.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜WTEA.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜WTEL.png
 ┃ ┃ ┃ ┃ ┃ ┣ 📜WTIA.png
 ┃ ┃ ┃ ┃ ┃ ┗ 📜WTIL.png
 ┃ ┃ ┃ ┃ ┗ 📂js
 ┃ ┃ ┃ ┃ ┃ ┣ 📂admin
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜datatables-simple-demo.js
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜scripts.js
 ┃ ┃ ┃ ┃ ┃ ┣ 📂assets
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂demo
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜chart-area-demo.js
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜chart-bar-demo.js
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜chart-pie-demo.js
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜datatables-demo.js
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂img
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜error-404-monochrome.svg
 ┃ ┃ ┃ ┃ ┃ ┣ 📂help
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜function.js
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜page.js
 ┃ ┃ ┃ ┃ ┃ ┗ 📂payment
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜orderlist.js
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜payment.js
 ┃ ┃ ┃ ┣ 📂templates
 ┃ ┃ ┃ ┃ ┣ 📂admin
 ┃ ┃ ┃ ┃ ┃ ┣ 📜businessAdmin.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜charts.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜chatdogAdmin.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜conferenceAdmin.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜dogLoveAdmin.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜findDogAdmin.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜index.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜inquiryAdmin.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜layout-sidenav-light.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜layout-static.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜login.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜memberRoleAdmin.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜password.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜patrolAdmin.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜patrolRecordAdmin.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜questionAdmin.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜register.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜salesListAdmin.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜tables.html
 ┃ ┃ ┃ ┃ ┃ ┗ 📜tables4.html
 ┃ ┃ ┃ ┃ ┣ 📂cart
 ┃ ┃ ┃ ┃ ┃ ┗ 📜cartList.html
 ┃ ┃ ┃ ┃ ┣ 📂community
 ┃ ┃ ┃ ┃ ┃ ┣ 📜chatdogcreate.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜chatdogdetail.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜chatdoglist.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜chatdogupdate.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜conferencecreate.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜conferencedetail.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜conferencelist.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜conferenceupdate.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜doglovecreate.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜doglovedetail.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜doglovelist.html
 ┃ ┃ ┃ ┃ ┃ ┗ 📜dogloveupdate.html
 ┃ ┃ ┃ ┃ ┣ 📂DBTI
 ┃ ┃ ┃ ┃ ┃ ┣ 📜dbticontent.html
 ┃ ┃ ┃ ┃ ┃ ┗ 📜start.html
 ┃ ┃ ┃ ┃ ┣ 📂dog4cuts
 ┃ ┃ ┃ ┃ ┃ ┣ 📜dog4cutsinsert.html
 ┃ ┃ ┃ ┃ ┃ ┗ 📜dog4cutsview.html
 ┃ ┃ ┃ ┃ ┣ 📂dogNum
 ┃ ┃ ┃ ┃ ┃ ┣ 📜dogNumForm.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜dogNumSave.html
 ┃ ┃ ┃ ┃ ┃ ┗ 📜test.html
 ┃ ┃ ┃ ┃ ┣ 📂error
 ┃ ┃ ┃ ┃ ┃ ┣ 📜denied.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜error.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜login.html
 ┃ ┃ ┃ ┃ ┃ ┗ 📜purchase-error.html
 ┃ ┃ ┃ ┃ ┣ 📂finddog
 ┃ ┃ ┃ ┃ ┃ ┣ 📂admin
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜findDogManage.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜detail.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜main.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜print.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜update.html
 ┃ ┃ ┃ ┃ ┃ ┗ 📜write.html
 ┃ ┃ ┃ ┃ ┣ 📂fragments
 ┃ ┃ ┃ ┃ ┃ ┗ 📜head.html
 ┃ ┃ ┃ ┃ ┣ 📂help
 ┃ ┃ ┃ ┃ ┃ ┣ 📂admin
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜helpManage.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📂inquiry
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜detail.html
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜list.html
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜update.html
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜write.html
 ┃ ┃ ┃ ┃ ┃ ┗ 📜main.html
 ┃ ┃ ┃ ┃ ┣ 📂honor
 ┃ ┃ ┃ ┃ ┃ ┗ 📜honor.html
 ┃ ┃ ┃ ┃ ┣ 📂main
 ┃ ┃ ┃ ┃ ┃ ┗ 📜main.html
 ┃ ┃ ┃ ┃ ┣ 📂map
 ┃ ┃ ┃ ┃ ┃ ┗ 📜map.html
 ┃ ┃ ┃ ┃ ┣ 📂member
 ┃ ┃ ┃ ┃ ┃ ┣ 📜businessRegistration.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜findUserId.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜findUserPwd.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜kakaoLogin.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜login.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜memberinfo.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜mypage.html
 ┃ ┃ ┃ ┃ ┃ ┗ 📜signup.html
 ┃ ┃ ┃ ┃ ┣ 📂patrol
 ┃ ┃ ┃ ┃ ┃ ┣ 📜patrol.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜patrolAdmin.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜patrolinsert.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜patrolRank.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜patrolRecord.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜patrolRecordAdmin.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜patrolRecordInsert.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜patrolRecordPlace.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜patrolRecordPlaceInsert.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜patrolRecordView.html
 ┃ ┃ ┃ ┃ ┃ ┗ 📜patrolview.html
 ┃ ┃ ┃ ┃ ┣ 📂payment
 ┃ ┃ ┃ ┃ ┃ ┣ 📜deliveryPopup.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜deliveryStatusPopup.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜orderHistory.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜orderHistoryDetails.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜orderlist.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜payment.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜paymentHistory.html
 ┃ ┃ ┃ ┃ ┃ ┗ 📜paymentHistoryDetails.html
 ┃ ┃ ┃ ┃ ┗ 📂sales
 ┃ ┃ ┃ ┃ ┃ ┣ 📜insertform.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜reviewPopup.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜reviewUpdatePopup.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜salesDetails.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜salesList.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜salesListAdmin.html
 ┃ ┃ ┃ ┃ ┃ ┣ 📜salesListBusiness.html
 ┃ ┃ ┃ ┃ ┃ ┗ 📜updateform.html

## 설치 및 실행 방법
- 프로젝트 설치 및 실행 방법 설명 예정

## 기여 방법
- 기여 방법 및 가이드라인 설명 예정

## 라이선스
- 프로젝트 라이선스 명시 예정

## 연락처
- 팀 이메일: team@example.com
- 팀 웹사이트: https://team.example.com
