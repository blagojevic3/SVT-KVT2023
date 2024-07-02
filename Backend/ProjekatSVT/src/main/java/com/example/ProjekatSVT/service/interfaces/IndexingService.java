package com.example.ProjekatSVT.service.interfaces;

import com.example.ProjekatSVT.model.DummyTable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IndexingService {

    DummyTable indexDocument(MultipartFile documentFile, String type, Integer id);
}
