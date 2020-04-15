package com.acnhcompanion.application.Sharing;

import java.io.Serializable;

public class Islander implements Serializable {
    String Friend_code;
    String Image;
    String Message;
    String Islander;
    String Island;

    public Islander (String Friend_code, String Image, String Message, String Islander, String Island){
        this.Friend_code = Friend_code;
        this.Image = Image;
        this.Message = Message;
        this.Islander = Islander;
        this.Island = Island;
    }
}
