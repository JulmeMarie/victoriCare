package com.victoricare.api.services;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.victoricare.api.services.impl.FileService.PropKey;
import com.victoricare.api.services.impl.FileService;
import com.victoricare.api.validators.impl.PageValidator;
import com.victoricare.api.repositories.IUserRepository;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.AmazonS3;
import com.victoricare.api.configurations.S3Config;
import com.victoricare.api.dtos.inputs.PageInputDTO;
import com.victoricare.api.entities.User;
import com.victoricare.api.enums.EMediaType;
import com.victoricare.api.exceptions.ActionNotAllowedException;
import com.victoricare.api.exceptions.ResourceNotFoundException;

@DisplayName("FileService Tests")
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FileServiceTest {

    private static final Integer USER_ID = 1;

    @Mock
    private S3Config s3Config;

    @Mock
    private IFileService fileService;

    @Mock
    private AmazonS3 amazonS3;

    @Mock
    private S3Object s3Object;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserService userService;

    @InjectMocks
    private FileService service;

    private MultipartFile mFile = Mockito.mock(MultipartFile.class);

    private Map<PropKey, Object> properties = new HashMap<PropKey, Object>();

    private S3ObjectInputStream s3ObjectInputStream = Mockito.mock(S3ObjectInputStream.class);;

    @BeforeEach
    public void setup() {

        this.properties.put(PropKey.FILE_NAME, "TEST_FILE_NAME");
        this.properties.put(PropKey.FILE, new File("TEST"));
        this.properties.put(PropKey.FILE_EXTENTION, "JPEG");
        this.properties.put(PropKey.FOLDER_NAME, "IMAGE");

        // Prepare data to return
        String data = "Hello, S3!";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data.getBytes());

        // Mock the read behavior
        try {
            when(s3ObjectInputStream.read(any(byte[].class)))
                    .thenAnswer(invocation -> {
                        byte[] buffer = invocation.getArgument(0);
                        int offset = 0;
                        int length = data.length();
                        int bytesRead = byteArrayInputStream.read(buffer, offset, length);
                        return bytesRead; // Return the number of bytes read or -1 if end of stream
                    });
            // Mock the close method
            doNothing().when(s3ObjectInputStream).close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        given(this.s3Config.getAmazonS3Client()).willReturn(amazonS3);
        given(this.s3Config.getS3BucketName()).willReturn("test_bucket");
    }

    @Test
    @Order(1)
    public void shouldUploadImageSuccessfully() {
        when(mFile.getOriginalFilename()).thenReturn("testfile.jpeg");
        // precondition
        try {
            willDoNothing().given(mFile).transferTo(Mockito.any(File.class));
        } catch (IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        given(this.s3Config.getImagesFolder()).willReturn("images");
        given(amazonS3.putObject(Mockito.any(PutObjectRequest.class))).willReturn(null);

        // action
        Map<PropKey, Object> props = service.doUpload(mFile, EMediaType.IMAGE.name());

        // verify the output
        assertThat(props).isNotNull(); // Verify creation date
        assertThat(props.size()).isEqualTo(4);
    }

    @Test
    @Order(1)
    public void shouldUploadAudioSuccessfully() {
        when(mFile.getOriginalFilename()).thenReturn("testfile.mp3");
        // precondition
        try {
            willDoNothing().given(mFile).transferTo(Mockito.any(File.class));
        } catch (IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        given(amazonS3.putObject(Mockito.any(PutObjectRequest.class))).willReturn(null);

        // action
        Map<PropKey, Object> props = service.doUpload(mFile, EMediaType.AUDIO.name());

        // verify the output
        assertThat(props).isNotNull(); // Verify creation date
        assertThat(props.size()).isEqualTo(4);
    }

    @Test
    @Order(1)
    public void shouldUploadVideoSuccessfully() {
        when(mFile.getOriginalFilename()).thenReturn("testfile.mp4");
        // precondition
        try {
            willDoNothing().given(mFile).transferTo(Mockito.any(File.class));
        } catch (IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        given(amazonS3.putObject(Mockito.any(PutObjectRequest.class))).willReturn(null);

        // action
        Map<PropKey, Object> props = service.doUpload(mFile, EMediaType.VIDEO.name());

        // verify the output
        assertThat(props).isNotNull(); // Verify creation date
        assertThat(props.size()).isEqualTo(4);
    }

    @Test
    @Order(1)
    public void shoulddoGetBase64Successfully() throws IOException {

        given(amazonS3.getObject(Mockito.anyString(), Mockito.anyString())).willReturn(s3Object);
        given(s3Object.getObjectContent()).willReturn(s3ObjectInputStream);

        // action
        String data64 = service.doGetBase64("images", "file.txt");
        assertThat(data64).isNotNull();
        // Verify that the close method was called
        verify(s3ObjectInputStream).close();
    }

    @Test
    @Order(1)
    public void shoulddoDeleteSuccessfully() throws IOException {
        willDoNothing().given(amazonS3).deleteObject(Mockito.any(DeleteObjectRequest.class));

        service.doDelete("images", "file.txt");

        // action
        verify(amazonS3, times(1)).deleteObject(Mockito.any(DeleteObjectRequest.class));
    }
}