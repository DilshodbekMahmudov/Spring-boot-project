package com.lesson.Springboot.service;

import com.lesson.Springboot.entity.FileStorage;
import com.lesson.Springboot.entity.enummration.FileStorageStatus;
import com.lesson.Springboot.repository.FileStorageRepository;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;


@Service
public class FileStorageService {

    private final FileStorageRepository fileStorageRepository;
    private final Hashids hashids;

    @Value("${upload.folder}")
    private String uploadFolder;


    public FileStorageService(FileStorageRepository fileStorageRepository) {
        this.fileStorageRepository = fileStorageRepository;
        this.hashids = new Hashids(getClass().getName(),6);
    }

    public FileStorage save(MultipartFile multipartFile) throws Exception{
        FileStorage fileStorage = new FileStorage();
        fileStorage.setName(multipartFile.getOriginalFilename());
        fileStorage.setFileSize(multipartFile.getSize());
        fileStorage.setContentType(multipartFile.getContentType());
        fileStorage.setExtension(getExt(multipartFile.getOriginalFilename()));
        fileStorage.setFileStorageStatus(FileStorageStatus.DRAFT);
        fileStorage = fileStorageRepository.save(fileStorage);

        Date now = new Date();
        File uploadFolder = new File(String.format("%s/upload_files/%d/%d/%d",this.uploadFolder,
                1900+ now.getYear(), 1+ now.getMonth(),now.getDate()));
        if (!uploadFolder.exists() && uploadFolder.mkdirs()){
            System.out.println("created folders");
        }

        fileStorage.setHashId(hashids.encode(fileStorage.getId()));
        fileStorage.setUploadFolder(String.format("upload_files/%d/%d/%d/%s.%s",
                1900+ now.getYear(),
                1+ now.getMonth(),
                now.getDate(),
                fileStorage.getHashId(),
                fileStorage.getExtension()));
        fileStorageRepository.save(fileStorage);
        uploadFolder = uploadFolder.getAbsoluteFile();
        File file  = new File(uploadFolder,String.format("%s.%s",fileStorage.getHashId(),fileStorage.getExtension()));

        try {
            multipartFile.transferTo(file);
        }catch (IOException e){
            e.printStackTrace();
        }


        return fileStorage;


    }

    public FileStorage findByHashId(String hashId){
        return fileStorageRepository.findByHashId(hashId);
    }

    public void delete(String hashId){
       FileStorage fileStorage = fileStorageRepository.findByHashId(hashId);
       File file = new File(String.format(String.format("%s/%s",this.uploadFolder,fileStorage.getUploadFolder())));
       if (file.delete()){
           fileStorageRepository.delete(fileStorage);

       }
    }

    private String getExt(String fileName) {
        String ext = null;
        if (fileName != null && !fileName.isEmpty()) {
            int dot = fileName.lastIndexOf('.');
            if (dot > 0 && dot <= fileName.length() - 2) {
                ext = fileName.substring(dot + 1);

            }

        }

        return ext;

    }

}