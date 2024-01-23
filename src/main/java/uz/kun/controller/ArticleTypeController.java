package uz.kun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.dto.ArticleTypeDTO;
import uz.kun.service.ArticleTypeService;

import java.util.Optional;

@RestController
@RequestMapping("/articleType")
public class ArticleTypeController {

    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping("")
    public ResponseEntity<ArticleTypeDTO>  create(@RequestBody ArticleTypeDTO dto){
        return ResponseEntity.ok(articleTypeService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleTypeDTO> getById(@PathVariable Integer id){
        return ResponseEntity.ok(articleTypeService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Integer id,
                                                 @RequestBody ArticleTypeDTO dto){
        return ResponseEntity.ok(articleTypeService.updateById(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Integer id){
        return ResponseEntity.ok(articleTypeService.deleteById(id));
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<ArticleTypeDTO>> pagination(@RequestParam Integer page,
                                                               @RequestParam Integer size){
        return ResponseEntity.ok(articleTypeService.pagination(page,size));
    }

    @GetMapping("/byLang")
    public ResponseEntity<Optional<ArticleTypeDTO>> getByLanguage(@RequestParam Integer id,
                                                                  @RequestParam String keyLang){
        return ResponseEntity.ok(articleTypeService.getByLang(id,keyLang));
    }
}
