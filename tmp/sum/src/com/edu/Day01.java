package com.edu;

public class Day01 {

    class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }
    //反转链表
    public ListNode ReverseList(ListNode head) {
        ListNode pre=null;
        ListNode next=null;
        while (head!=null){
            next=head.next;
            head.next=pre;
            pre=head;
            head=next;
        }
        return pre;
    }
    //    薯队长写了一篇笔记草稿，请你帮忙输出最后内容。
    //1.输入字符包括，"("    ,    ")"    和    "<"和其他字符。
    //2.其他字符表示笔记内容。
    //3.()之间表示注释内容，任何字符都无效。    括号保证成对出现。
    //4."<"表示退格,    删去前面一个笔记内容字符。括号不受"<"影响    。
    public String blog(String str){
        StringBuilder newStr = new StringBuilder(str);
        char[] chars = str.toCharArray();
        int start=0;
        int end=0;
        int countStart=0;
        for (int i = 0; i <chars.length ; i++) {
            if(chars[i]=='('){
                start=i;
            }
            if(chars[i]==')'){
                end=i;
                newStr.delete(start,end);
            }
            if(chars[i]=='<'){
                countStart=i;
                newStr.delete(countStart-1,countStart);
            }
        }
        return newStr.toString();
    }
}
