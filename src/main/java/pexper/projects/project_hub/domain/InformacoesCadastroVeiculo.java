package pexper.projects.project_hub.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class InformacoesCadastroVeiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;
    private String placa;
    private String renavam;
    private String chassi;
    private String tipoVeiculo;
    private String categoria;
    private String marcaModelo;
    private String anoFabricacao;
    private String anoModelo;
    private String cor;
    private String capacidadeCargaPassageiros;
    private String quilometragemAtual;
    private String crlv;
    private String situacaoIpva;
    private String vencimentoLicenciamento;
    private String situacaoMultas;
    private String seguroObrigatorioDpvatESeguroPrivado;
    private String dataVencimentoSeguro;
    private String empresaOuTerceiro;
    private String nomeProprietario;
    private String cnpjCpf;
    private String historicoRevisoes;
    private String proximaRevisaoProgramada;
    private String trocaOleo;
    private String manutencaoPneus;
    private String registroAvariasConsertos;
    private String tipoCombustivel;
    private String consumoMedio;
    private String historicoAbastecimento;
    private String observacao1;
    private String observacao2;
}
