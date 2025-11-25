package br.utfpr.tcc.servico_relatorios.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Entity
@Data
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Seção 1: Identificação da Propriedade ---
    private String ownerName;
    private String document; // CPF/CNPJ
    private String propertyName;
    private Double totalArea;
    @Lob
    private String address;

    // --- Seção 2: Informações Geográficas ---
    private String latitude;
    private String longitude;
    private String reliefType;
    private String soilUse;

    // --- Seção 3: Dados do Cultivo ---
    private String cropType;
    private String developmentStage;
    private Double plantingDensity;
    private String cultivationMethod;

    // --- Dados do Frontend ---
    @Lob
    private String encryptedData;
}