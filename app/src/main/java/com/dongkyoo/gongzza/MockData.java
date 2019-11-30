package com.dongkyoo.gongzza;

import com.dongkyoo.gongzza.dtos.CourseDto;
import com.dongkyoo.gongzza.dtos.PostDto;
import com.dongkyoo.gongzza.vos.Course;
import com.dongkyoo.gongzza.vos.CourseInfo;
import com.dongkyoo.gongzza.vos.HashTag;
import com.dongkyoo.gongzza.vos.Participant;
import com.dongkyoo.gongzza.vos.Post;
import com.dongkyoo.gongzza.vos.Token;
import com.dongkyoo.gongzza.vos.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 작성자: 이동규
 * 가짜 데이터를 리턴해주는 클래스. 테스트를 위해 사용
 */
public class MockData {

    public static List<String> getMockHashTagList() {
        return Arrays.asList("운동", "게임", "노래방", "밥", "abc", "def", "ghi");
    }

    public static User getMockUser() {
        return new User("testId", "testName", "testPassword", new Date(System.currentTimeMillis()), 1, "wind.dong.dream@gmail.com");
    }

    public static List<Post> getMockPostList() {
        return Arrays.asList(
                new Post(1, getMockUser().getId(), "카페가실분", "케이크팝 가실분 구해요", new Date(), new Date(), 4, null),
                new Post(2, getMockUser().getId(),  "농구하실분", "농구 하실분 한 분 구해요", new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3), new Date(), 4, null)
        );
    }

    public static PostDto getMockPostDto() {
        return new PostDto(0, getMockUser().getId(), "농구하실분", "농구 하실분 한 분 구해요",
                new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3),
                new Date(), 4, null,
                Arrays.asList(new Participant(0, getMockUser())),
                Arrays.asList(
                        new HashTag("#FFAAAA", "180이상"),
                        new HashTag("#FFAAAA", "유쾌"),
                        new HashTag("#FFAAAA", "상쾌"),
                        new HashTag("#FFAAAA", "통쾌"),
                        new HashTag("#FFAAAA", "적당히 긴     텍스트")
                )
        );
    }

    public static Participant getMockParticipant() {
        return new Participant(MockData.getMockPostDto().getId(), MockData.getMockUser());
    }

    public static CourseDto getMockCourseDto() {
        Course course = getMockCourse();
        List<CourseInfo> courseInfoList = new ArrayList<>();
        courseInfoList.add(getMockCourseInfo());
        courseInfoList.add(getMockCourseInfo());

        return new CourseDto(
                course.getId(),
                course.getUserId(),
                course.getName(),
                course.getProfessor(),
                courseInfoList
        );
    }

    public static Course getMockCourse() {
        User user = getMockUser();
        return new Course(0, user.getId(), "사용자 인터페이스", "최지웅");
    }

    public static CourseInfo getMockCourseInfo() {
        Course course = getMockCourse();
        return new CourseInfo(0, course.getId(), "12:00", "13:50", 1);
    }

    public static Token getMockToken() {
        return new Token(getMockUser().getId(), "ldfajsdklj2", new Date());
    }
}
