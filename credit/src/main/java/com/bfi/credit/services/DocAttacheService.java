package com.bfi.credit.services;

import com.bfi.credit.entities.DocAttache;
import com.bfi.credit.repositories.DocAttacheRepository;
import com.bfi.credit.services.interfaces.IDocAttacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

@Service
@RequestMapping("/docattache")
@RequiredArgsConstructor
public class DocAttacheService implements IDocAttacheService {

    @Autowired
    private DocAttacheRepository docAttacheRepository;

    @Override
    public DocAttache saveDocAttache(DocAttache docAttache) {
        return docAttacheRepository.save(docAttache);
    }

    @Override
    public DocAttache updateDocAttache(DocAttache docAttache) {
        return docAttacheRepository.save(docAttache);
    }

    @Override
    public void deleteDocAttache(Long id) {
        docAttacheRepository.deleteById(id);
    }

    @Override
    public DocAttache getDocAttacheById(Long id) {
        return docAttacheRepository.findById(id).orElse(null);
    }

    @Override
    public List<DocAttache> getAllDocAttaches() {
        return docAttacheRepository.findAll();
    }




    @Override
    public Boolean deletePieceJointe(Long idP) {
        Optional<DocAttache> PieceJointeOptional = docAttacheRepository.findById(idP);
        if (PieceJointeOptional.isPresent()) {
            docAttacheRepository.deleteById(idP);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<DocAttache> getAllPieceJointe() {
        return (List<DocAttache>) docAttacheRepository.findAll();
    }

    @Override
    public DocAttache getPieceJointeById(Long idP) {
        return docAttacheRepository.findById(idP).orElse(null);
    }

    public void saveDocAttache(MultipartFile file, boolean obligatoire) throws Exception {

        DocAttache docAttache = new DocAttache();
        docAttache.setNomFichier(file.getOriginalFilename());
        docAttache.setTypeMime(file.getContentType());
        docAttache.setTaille(file.getSize());
        docAttache.setData(file.getBytes());
        docAttache.setObligatoire(obligatoire);

        // Save PieceJointe
        docAttacheRepository.save(docAttache);


    }
}

