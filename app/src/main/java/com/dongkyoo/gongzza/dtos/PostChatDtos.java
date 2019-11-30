package com.dongkyoo.gongzza.dtos;

import com.dongkyoo.gongzza.vos.ChatLog;

import java.util.Collections;
import java.util.List;

public class PostChatDtos {

    public static void merge(List<PostChatDto> src, List<PostChatDto> dest) {
        for (PostChatDto postChatDto : src) {
            boolean isFound = false;
            for (PostChatDto destPostChatDto : dest) {
                if (postChatDto.getId() == destPostChatDto.getId()) {
                    isFound = true;
                    for (ChatLog c : postChatDto.getChatLogList()) {
                        if (!destPostChatDto.getChatLogList().contains(c)) {
                            destPostChatDto.getChatLogList().add(c);
                        }
                    }
                    destPostChatDto.sortChatLogList();
                    break;
                }
            }

            if (!isFound) {
                dest.add(postChatDto);
            }
        }

        Collections.sort(dest);
    }
}
