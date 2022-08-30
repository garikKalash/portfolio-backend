package com.gk.portfolio.services

import com.amazonaws.services.s3.AmazonS3
import com.gk.portfolio.AppIntegrationTest
import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile


class S3CloudServiceTest extends AppIntegrationTest {

    @Autowired
    S3CloudService s3CloudService

    MultipartFile multipartFile

    @Before
    MultipartFile getFile() {
        File file = new File(Objects.requireNonNull(S3CloudServiceTest.class.getClassLoader()
                .getResource("file.txt")).getFile());
        FileInputStream inputStream = new FileInputStream(file)
        multipartFile = new MockMultipartFile("file", file.getName(),
                "text/plain", inputStream)
        return multipartFile
    }

    def "upload file"() {

        def size = s3CloudService.fileNames().size()

        when:
        s3CloudService.upload(multipartFile)
        def files = s3CloudService.fileNames()

        then:
        s3CloudService.fileNames().size() == size + 1
        files.contains("new_file.txt")

        cleanup:
        s3CloudService.delete("new_file.txt")


    }

    def "download file"() {

        s3CloudService.upload(multipartFile)
        byte [] bytes = multipartFile.getBytes()


        when:
        ByteArrayOutputStream baos = s3CloudService.download("new_file.txt")

        then:
        baos.toByteArray().size() == bytes.size()
        baos.toByteArray() == bytes

        cleanup:
        s3CloudService.delete("new_file.txt")



    }

    def "delete file"() {

        s3CloudService.upload(multipartFile)
        def size = s3CloudService.fileNames().size()

        when:
        s3CloudService.delete("new_file.txt")

        then:
       s3CloudService.fileNames().size() == size - 1

    }



}
