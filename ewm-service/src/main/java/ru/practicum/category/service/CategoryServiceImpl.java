package ru.practicum.category.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.CategoryMapper;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.receive.ReceiveService;


import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final ReceiveService receiveService;

    @Transactional
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = CategoryMapper.returnCategory(categoryDto);
        categoryRepository.save(category);

        return CategoryMapper.returnCategoryDto(category);
    }

    @Transactional
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {

        Category category = receiveService.getCategoryOrNotFound(categoryId);
        category.setName(categoryDto.getName());
        categoryRepository.save(category);

        return CategoryMapper.returnCategoryDto(category);
    }

    @Transactional
    @Override
    public void deleteCategory(Long categoryId) {

        receiveService.getCategoryOrNotFound(categoryId);

        if (!eventRepository.findByCategoryId(categoryId).isEmpty()) {
            throw new ConflictException(String.format("This category id %s is used and cannot be deleted", categoryId));
        }

        categoryRepository.deleteById(categoryId);
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {

        PageRequest pageRequest = PageRequest.of(from / size, size);

        return CategoryMapper.returnCategoryDtoList(categoryRepository.findAll(pageRequest));
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {

        return CategoryMapper.returnCategoryDto(receiveService.getCategoryOrNotFound(categoryId));
    }
}