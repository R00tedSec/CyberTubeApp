package com.cybertube.web.Videos;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoService {

    @Autowired
    VideoRepository videoRepository;

    /**
     * public void initVideo() { if (this.videoRepository.findAll().isEmpty()) {
     * List<String> list = new ArrayList<>(); list.add("OSINT");
     * videoRepository.save(new Video( "Introducción a los conceptos básicos y
     * contexto actual de la ciberseguridad, pilares básicos.",
     * "https://youtu.be/LZkZZi7eR-0", "Principios de la ciberseguridad en el
     * contexto actual", "Marta Beltrán", list)); videoRepository.save(new
     * Video("Configuración segura de dispositivos Android",
     * "https://youtu.be/t6S7j6hKNPI", "El CCN-CERT se encarga de la gestión de
     * ciberincidentes que afecten a sistemas clasificados, de las Administraciones
     * Públicas y de empresas y organizaciones de interés estratégico para el país.
     * Su misión, por tanto, es contribuir a la mejora de la ciberseguridad
     * española, siendo el centro de alerta y respuesta nacional que coopere. En
     * este video nos mostrarán cómo proteger nuestros dispositivos Android.",
     * "CCN-CERT", list)); videoRepository.save(new Video("BlueKeep - Exploit
     * windows (RDP Vulnerability) Remote Code Execution",
     * "https://youtu.be/y-KsMgswEuk", "BlueKeep CVE-2019-0708 is a critical Remote
     * Code Execution vulnerability in Microsoft’s RDP service. This only targets
     * Windows 2008 R2 and Windows 7 SP1.\r\n\r\nMetasploit is releasing an initial
     * public exploit module for CVE-2019-0708, also known as BlueKeep, as a pull
     * request on Metasploit Framework.\r\nSince the vulnerability is wormable, it
     * has caught a great deal of attention from the security community, being in
     * the same category with EternalBlue MS17-010.\r\n", "@kalipentesting", list));
     * videoRepository.save(new Video( "Aplicación de técnicas OSINT para la
     * detección y análisis de perfiles terroristas",
     * "https://youtu.be/gnbYFtfcSFQ", "Conoceremos, mediante ejercicios prácticos
     * en directo, el uso de las RRSS por parte de grupos terroristas, y el valor
     * que aportan las disciplinas #OSINT / #SOCMINT para la monitorización de sus
     * actividades y la identificación de perfiles. \r\nPresentación de las últimas
     * versiones de las herramientas \"tinfoleak\" y \"magneto\", referentes en las
     * disciplinas OSINT / SOCMINT, de la mano de sus creadores (Vicente Aguilera y
     * Carlos Seisdedos). El taller incluirá parte teórica y práctica, y se
     * ejecutarán ejercicios en vivo de análisis de redes sociales para la
     * extracción de información que pueda convertirse en inteligencia procesable.",
     * "INCIBE (Vicente Aguilera Diaz y Carlos Seisdedos)", list)); } }
     */
    public void saveVideo(Video videoToSave) {
        this.videoRepository.saveAndFlush(videoToSave);
    }

    public void addVideo(final Video video) {
        videoRepository.save(video);
    }

    public void removeVideo(final long id) {
        if (videoRepository.existsById(id)) {
            videoRepository.deleteById(id);
        }
    }

    public void updateVideo(final long id, String title, String URL, String description, String author,
            List<String> category) {
        if (this.videoRepository.existsById(id)) {
            Video auxVideo = this.videoRepository.getOne(id);
            if (!title.equals(null))
                auxVideo.setVideoTitle(title);

            if (!category.equals(null))
                auxVideo.setVideoCategories(category);

            if (!author.equals(null))
                auxVideo.setVideoAuthor(author);

            if (!description.isEmpty()) {
                auxVideo.setVideoDescription(description);
            } else {
                auxVideo.setVideoDescription(auxVideo.getVideoDescription());
            }

            this.videoRepository.saveAndFlush(auxVideo);
        }
    }

    public void updateVideo(Video video) {
        if (this.videoRepository.existsById(video.getVideoID())) {
            this.videoRepository.saveAndFlush(video);

        }

    }

    public Video findById(final long id) {
        return this.videoRepository.findById(id).get();
    }

    public List<Video> findAll() {
        return videoRepository.findAll();
    }

    public List<Video> findLastVideos() {
        List<Video> allvideos = videoRepository.findAll();
        List<Video> lastVideos = new ArrayList<>();
        if (allvideos.size() > 2) {
            for (int i = allvideos.size() - 1; i > allvideos.size() - 4; i--) {
                lastVideos.add(allvideos.get(i));
            }
        }
        return lastVideos;
    }

}