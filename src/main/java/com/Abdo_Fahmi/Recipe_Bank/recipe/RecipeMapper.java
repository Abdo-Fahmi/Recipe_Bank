package com.Abdo_Fahmi.Recipe_Bank.recipe;

import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

@Component
@UtilityClass
public class RecipeMapper {
    public RecipeDTO toDTO(Recipe entity) {
        return RecipeDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .tags(entity.getTags())
                .ingredients(entity.getIngredients())
                .ownerId(entity.getOwnerId())
                .build();
    }
    public Recipe toEntity(RecipeDTO dto) {
        return Recipe.builder()
                .id(dto.id())
                .name(dto.name())
                .description(dto.description())
                .tags(dto.tags())
                .ingredients(dto.ingredients())
                .ownerId(dto.ownerId())
                .build();
    }


}
