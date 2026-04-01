package com.algaworks.brewer.storage.local;

import com.algaworks.brewer.storage.FotoStorage;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

@Profile("local")
@Component
public class FotoStorageLocal implements FotoStorage{

    private final static Logger LOGGER = LoggerFactory.getLogger(FotoStorageLocal.class);
    private static final String THUMBNAIL_PREFIX = "thumbnail.";
    
    private Path local;

    public FotoStorageLocal() {
    		//NO WINDOWS EU USEI APENAS System.getProperty("user.home") que é para todos, inclusive windows, PARA MAC e LINUX, É: System.getenv("HOME")
        this(FileSystems.getDefault().getPath(System.getProperty("user.home"), ".brewerfotos"));
    }

    public FotoStorageLocal(Path path){
        this.local = path;
        criarPastas();
    }
    
    @Override
    public String salvar(MultipartFile[] files) {
        String novoNome = null;
        if(files != null && files.length > 0){
            MultipartFile arquivo = files[0];
            novoNome = renomearArquivo(arquivo.getOriginalFilename());
            try {
                arquivo.transferTo(new File(this.local.toAbsolutePath().toString() + FileSystems.getDefault().getSeparator() + novoNome));
            } catch (IOException e) {
                throw new RuntimeException("Erro salvando a foto.");
            }
        }
        
        try {
            Thumbnails.of(this.local.resolve(novoNome).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
        } catch (IOException e) {
            throw new RuntimeException("Erro gerando thumbnail...", e);
        }
        
        return novoNome;
    }  
    
    @Override
    public byte[] recuperar(String nome) {
       try {
           return Files.readAllBytes(this.local.resolve(nome));
       } catch (IOException e) {
           throw new RuntimeException("Erro lendo foto da pasta ", e);
       }
    }
    
    @Override
	public byte[] recuperarThumbnail(String fotoCerveja) {
		
		return recuperar(THUMBNAIL_PREFIX + fotoCerveja );
	}
    
	@Override
	public void excluir(String foto) {
		try {
			Files.deleteIfExists(this.local.resolve(foto));
			Files.deleteIfExists(this.local.resolve(THUMBNAIL_PREFIX + foto));
		}catch(IOException e) {
			LOGGER.warn(String.format("Erro apagando foto '%s'. Mensagem: %s ", foto, e.getMessage()));
		}
	}
	
	@Override
	public String getUrl(String foto) {
		return "http://localhost:8080/brewer/fotos/" + foto;
	}

    private void criarPastas() {
        try {
            Files.createDirectories(this.local);           
            
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("Pastas criadas para salvar fotos");
                LOGGER.debug("Pasta default " + this.local.toAbsolutePath());
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro criando pasta para salvar as fotos", e);
        }
    }
	
}
