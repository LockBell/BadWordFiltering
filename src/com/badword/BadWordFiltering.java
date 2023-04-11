package com.badword;

import com.badword.method.EditWord;
import com.badword.method.ReadFile;
import com.badword.method.ReadURL;
import com.badword.words.BadWords;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BadWordFiltering implements BadWords, ReadFile {
    private final Set<String> set = new HashSet<>(List.of(koreaWord1));
    private String substituteValue = "*";

    //대체 문자 지정
    //기본값 : *
    public BadWordFiltering(String substituteValue) {
        this.substituteValue = substituteValue;
    }

    public BadWordFiltering() {}

    //특정 문자 추가, 삭제
    @Override
    public void add(String text) {
        set.add(text);
    }

    @Override
    public void add(String...texts) {
        set.addAll(List.of(texts));
    }

    @Override
    public void add(List<String> texts) {
        set.addAll(texts);
    }

    @Override
    public void add(Set<String> texts) {
        set.addAll(texts);
    }

    @Override
    public void remove(String...texts) {
        List.of(texts).forEach(set::remove);
    }

    @Override
    public void remove(List<String> texts) {
        texts.forEach(set::remove);
    }

    @Override
    public void remove(Set<String> texts) {
        texts.forEach(set::remove);
    }

    //비속어 있다면 대체
    public String checkAndChange(String text) {
        Set<String> s = set.stream()
                .filter(text::contains)
                .collect(Collectors.toSet());

        for (String v : s) {
            int textLen = v.length();
            String sub = this.substituteValue.repeat(textLen);
            text = text.replace(v, sub);
        }

        return text;
    }

    //비속어가 1개라도 존재하면 true 반환
    public boolean check(String text) {
        return set.stream()
                .anyMatch(text::contains);
    }

    //공백을 없는 상태 체크
    public boolean blankCheck(String text) {
        return check(text.replace(" ", ""));
    }
}