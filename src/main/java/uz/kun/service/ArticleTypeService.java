package uz.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import uz.kun.dto.ArticleTypeDTO;
import uz.kun.entity.ArticleTypeEntity;
import uz.kun.exception.AppBadException;
import uz.kun.repository.ArticleTypeRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService implements CrudService<ArticleTypeDTO, Integer> {

    @Autowired
    private ArticleTypeRepository articleTypeRepository;
    @Override
    public ArticleTypeDTO create(ArticleTypeDTO dto) {
        ArticleTypeEntity entity=new ArticleTypeEntity();

        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setVisible(true);
        entity.setCreatedDate(LocalDateTime.now());

        articleTypeRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;


    }

    @Override
    public ArticleTypeDTO findById(Integer id) {
        Optional<ArticleTypeEntity> optional = articleTypeRepository.findById(id);
        if(optional.isEmpty()){
            throw new AppBadException("Article type not found❌❌❌");
        }

        ArticleTypeDTO dto = toDTO(optional.get());

        return dto;

    }

    @Override
    public Boolean updateById(Integer id, ArticleTypeDTO dto) {
        Optional<ArticleTypeEntity> optional = articleTypeRepository.findById(id);

        if (optional.isEmpty()) {
            throw new AppBadException("ArticleType has not found with this id❌");
        }

        ArticleTypeEntity entity=optional.get();

        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setOrderNumber(dto.getOrderNumber());

        articleTypeRepository.save(entity);


        return true;

    }

    @Override
    public Boolean deleteById(Integer id) {
        Optional<ArticleTypeEntity> optional = articleTypeRepository.findById(id);

        if (optional.isEmpty()) {
            throw new AppBadException("ArticleType has not found with this id❌");
        }

        articleTypeRepository.delete(optional.get());
        return true;
    }

    public PageImpl<ArticleTypeDTO> pagination(Integer page, Integer size) {

        Sort sort=Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable= PageRequest.of(page-1,size,sort);
        Page<ArticleTypeEntity> entityPage = articleTypeRepository.findAllBy(pageable);

        List<ArticleTypeEntity> content = entityPage.getContent();
        long totalElements = entityPage.getTotalElements();

        List<ArticleTypeDTO> dtoList=new LinkedList<>();

        for (ArticleTypeEntity articleTypeEntity : content) {
            dtoList.add(toDTO(articleTypeEntity));
        }

        return new PageImpl<>(dtoList,pageable,totalElements);


    }

    public Optional<ArticleTypeDTO> getByLang(Integer id, String lang) {
        Optional<ArticleTypeEntity> articleTypeOptional = articleTypeRepository.findById(id);

        if (articleTypeOptional.isEmpty()) {
           throw new AppBadException("Lang Not found ❌❌❌");
        }
        ArticleTypeEntity articleType = articleTypeOptional.get();
        ArticleTypeDTO articleTypeDTO = new ArticleTypeDTO();
        articleTypeDTO.setId(articleType.getId());
        articleTypeDTO.setOrderNumber(articleType.getOrderNumber());

        switch (lang) {
            case "uz" -> articleTypeDTO.setNameUz(articleType.getNameUz());
            case "ru" -> articleTypeDTO.setNameRu(articleType.getNameRu());
            case "en" -> articleTypeDTO.setNameEn(articleType.getNameEn());
            default ->
                    articleTypeDTO.setNameUz( articleType.getNameUz());
        };

        return Optional.of(articleTypeDTO);

    }

    public ArticleTypeDTO toDTO(ArticleTypeEntity entity){
        ArticleTypeDTO articleTypeDTO=new ArticleTypeDTO();
        articleTypeDTO.setId(entity.getId());
        articleTypeDTO.setNameEn(entity.getNameEn());
        articleTypeDTO.setNameRu(entity.getNameRu());
        articleTypeDTO.setNameUz(entity.getNameUz());
        articleTypeDTO.setCreatedDate(entity.getCreatedDate());
        articleTypeDTO.setOrderNumber(entity.getOrderNumber());
        articleTypeDTO.setUpdatedDate(entity.getUpdatedDate());
        articleTypeDTO.setVisible(entity.getVisible());

        return articleTypeDTO;
    }


}
