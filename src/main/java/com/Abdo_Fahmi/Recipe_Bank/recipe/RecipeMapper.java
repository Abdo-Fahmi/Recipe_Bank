package com.Abdo_Fahmi.Recipe_Bank.recipe;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RecipeMapper {
    public RecipeDTO toDTO(Recipe entity) {
        return RecipeDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .instructions(entity.getInstructions())
                .tags(entity.getTags())
                .ingredients(entity.getIngredients())
                .ownerId(entity.getOwnerId())
                .isPublic(entity.isPublic())
                .build();
    }

    public RecipeProxyDTO toProxyDTO(Recipe entity) {
        return RecipeProxyDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public Recipe toEntity(RecipeCreationRequest dto) {
        return Recipe.builder()
                .name(dto.name())
                .description(dto.description())
                .instructions(dto.instructions())
                .tags(dto.tags())
                .ingredients(dto.ingredients())
                .isPublic(dto.isPublic())
                .build();
    }

    public Recipe toEntity(RecipeDTO dto) {
        return Recipe.builder()
                .id(dto.id())
                .name(dto.name())
                .description(dto.description())
                .instructions(dto.instructions())
                .tags(dto.tags())
                .ingredients(dto.ingredients())
                .ownerId(dto.ownerId())
                .isPublic(dto.isPublic())
                .build();
    }
}
