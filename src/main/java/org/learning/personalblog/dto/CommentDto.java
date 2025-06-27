package org.learning.personalblog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    @NotBlank(message="Content cannot be empty")
    private String content;

}
