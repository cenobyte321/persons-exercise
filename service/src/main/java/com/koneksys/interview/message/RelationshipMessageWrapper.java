package com.koneksys.interview.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RelationshipMessageWrapper implements Serializable {

    List<RelationshipMessage> messageList = new ArrayList<>();

    public List<RelationshipMessage> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<RelationshipMessage> messageList) {
        this.messageList = messageList;
    }
}
