package pexper.projects.project_hub.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pexper.projects.project_hub.domain.Address;
import pexper.projects.project_hub.domain.CadastroContrato;
import pexper.projects.project_hub.domain.CadastroUsuario;
import pexper.projects.project_hub.domain.Chamado;
import pexper.projects.project_hub.domain.Documentacao;
import pexper.projects.project_hub.domain.Faturamento;
import pexper.projects.project_hub.domain.File;
import pexper.projects.project_hub.domain.InformacoesCadastroVeiculo;
import pexper.projects.project_hub.domain.Owner;
import pexper.projects.project_hub.domain.Project;
import pexper.projects.project_hub.domain.SiteClaro;
import pexper.projects.project_hub.domain.SiteTim;
import pexper.projects.project_hub.domain.SiteVivo;
import pexper.projects.project_hub.repositories.AddressRepository;
import pexper.projects.project_hub.repositories.CadastroContratoRepository;
import pexper.projects.project_hub.repositories.CadastroUsuarioRepository;
import pexper.projects.project_hub.repositories.ChamadoRepository;
import pexper.projects.project_hub.repositories.DocumentacaoRepository;
import pexper.projects.project_hub.repositories.FaturamentoRepository;
import pexper.projects.project_hub.repositories.FileRepository;
import pexper.projects.project_hub.repositories.InformacoesCadastroVeiculoRepository;
import pexper.projects.project_hub.repositories.OwnerRepository;
import pexper.projects.project_hub.repositories.ProjectRepository;
import pexper.projects.project_hub.repositories.SiteClaroRepository;
import pexper.projects.project_hub.repositories.SiteTimRepository;
import pexper.projects.project_hub.repositories.SiteVivoRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("h2")
public class BootstrapData implements CommandLineRunner {

    private final ProjectRepository projectRepository;
    private final OwnerRepository ownerRepository;
    private final AddressRepository addressRepository;
    private final FileRepository fileRepository;
    private final DocumentacaoRepository documentacaoRepository;
    private final FaturamentoRepository faturamentoRepository;
    private final InformacoesCadastroVeiculoRepository informacoesCadastroVeiculoRepository;
    private final CadastroUsuarioRepository cadastroUsuarioRepository;
    private final SiteClaroRepository siteClaroRepository;
    private final SiteTimRepository siteTimRepository;
    private final SiteVivoRepository siteVivoRepository;
    private final CadastroContratoRepository cadastroContratoRepository;
    private final ChamadoRepository chamadoRepository;

    public BootstrapData(ProjectRepository projectRepository,
                         OwnerRepository ownerRepository,
                         AddressRepository addressRepository,
                         FileRepository fileRepository,
                         DocumentacaoRepository documentacaoRepository,
                         FaturamentoRepository faturamentoRepository,
                         InformacoesCadastroVeiculoRepository informacoesCadastroVeiculoRepository,
                         CadastroUsuarioRepository cadastroUsuarioRepository,
                         SiteClaroRepository siteClaroRepository,
                         SiteTimRepository siteTimRepository,
                         SiteVivoRepository siteVivoRepository,
                         CadastroContratoRepository cadastroContratoRepository,
                         ChamadoRepository chamadoRepository) {
        this.projectRepository = projectRepository;
        this.ownerRepository = ownerRepository;
        this.addressRepository = addressRepository;
        this.fileRepository = fileRepository;
        this.documentacaoRepository = documentacaoRepository;
        this.faturamentoRepository = faturamentoRepository;
        this.informacoesCadastroVeiculoRepository = informacoesCadastroVeiculoRepository;
        this.cadastroUsuarioRepository = cadastroUsuarioRepository;
        this.siteClaroRepository = siteClaroRepository;
        this.siteTimRepository = siteTimRepository;
        this.siteVivoRepository = siteVivoRepository;
        this.cadastroContratoRepository = cadastroContratoRepository;
        this.chamadoRepository = chamadoRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("BootstrapData starting...");

        List<Project> projects = new ArrayList<>();
        String[] projectNames = {
                "Atlas Migration",
                "Nimbus Analytics",
                "Orion Console",
                "Nova CRM",
                "Zenith Payments",
                "Pulse Inventory",
                "Harbor Compliance",
                "Vertex Mobile",
                "Lumen AI",
                "Summit Scheduler"
        };

        for (String name : projectNames) {
            var project = new Project();
            project.setProjectName(name);
            projects.add(project);
        }

        List<Owner> owners = new ArrayList<>();
        String[][] ownerData = {
                {"Ana Souza", "ana.souza@example.com"},
                {"Bruno Lima", "bruno.lima@example.com"},
                {"Carla Mendes", "carla.mendes@example.com"},
                {"Diego Santos", "diego.santos@example.com"},
                {"Eduarda Freitas", "eduarda.freitas@example.com"},
                {"Fabio Ramos", "fabio.ramos@example.com"},
                {"Gabriela Costa", "gabriela.costa@example.com"},
                {"Helena Moraes", "helena.moraes@example.com"},
                {"Igor Pereira", "igor.pereira@example.com"},
                {"Julia Nogueira", "julia.nogueira@example.com"},
                {"Kaique Oliveira", "kaique.oliveira@example.com"},
                {"Larissa Rocha", "larissa.rocha@example.com"},
                {"Marcos Araujo", "marcos.araujo@example.com"},
                {"Nadia Rezende", "nadia.rezende@example.com"},
                {"Otavio Teixeira", "otavio.teixeira@example.com"}
        };

        for (String[] data : ownerData) {
            var owner = new Owner();
            owner.setName(data[0]);
            owner.setEmail(data[1]);
            owners.add(owner);
        }

        for (int i = 0; i < owners.size(); i++) {
            Owner owner = owners.get(i);
            Project primary = projects.get(i % projects.size());
            Project secondary = projects.get((i + 3) % projects.size());

            owner.getProjects().add(primary);
            owner.getProjects().add(secondary);
            primary.getOwners().add(owner);
            secondary.getOwners().add(owner);
        }

        ownerRepository.saveAll(owners);
        projectRepository.saveAll(projects);

        List<File> files = new ArrayList<>();
        for (int i = 1; i <= 80; i++) {
            Project project = projects.get((i - 1) % projects.size());
            String slug = slugify(project.getProjectName());

            var file = new File();
            file.setFilename("file-" + String.format("%03d", i) + ".txt");
            file.setPath("/projects/" + slug + "/docs/file-" + String.format("%03d", i) + ".txt");
            file.setProject(project);
            project.getFiles().add(file);
            files.add(file);
        }
        fileRepository.saveAll(files);

        List<Address> addresses = new ArrayList<>();
        for (int i = 0; i < owners.size(); i++) {
            Owner owner = owners.get(i);
            var address = new Address();
            address.setStreet("Owner Street " + (i + 1));
            address.setCity("Sao Paulo");
            address.setState("SP");
            address.setNumber(String.valueOf(100 + i));
            address.setZipCode("0100" + i + "-000");
            address.setOwner(owner);
            owner.setAddress(address);
            addresses.add(address);
        }

        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            var address = new Address();
            address.setStreet("Project Avenue " + (i + 1));
            address.setCity("Campinas");
            address.setState("SP");
            address.setNumber(String.valueOf(500 + i));
            address.setZipCode("1301" + i + "-100");
            address.setProject(project);
            project.setAddress(address);
            addresses.add(address);
        }

        addressRepository.saveAll(addresses);
        ownerRepository.saveAll(owners);
        projectRepository.saveAll(projects);

        List<Documentacao> documentacoes = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            Documentacao doc = new Documentacao();
            doc.setTipoDocumentacao("DOC-" + i);
            doc.setClienteDireto("Cliente Direto " + i);
            doc.setGestorClienteDireto("Gestor Direto " + i);
            doc.setClienteFinal("Cliente Final " + i);
            doc.setGestorClienteFinal("Gestor Final " + i);
            doc.setProjetoTipo("Tipo Projeto " + ((i % 3) + 1));
            doc.setProjetoNumero("PRJ-" + String.format("%04d", i));
            doc.setOc("OC-" + String.format("%03d", i));
            doc.setOs("OS-" + String.format("%03d", i));
            doc.setTecnico("Tecnico " + i);
            doc.setEndereco("Rua Exemplo " + i);
            doc.setNumero(String.valueOf(100 + i));
            doc.setComplemento("Sala " + ((i % 5) + 1));
            doc.setBairro("Bairro " + ((i % 4) + 1));
            doc.setCidade("Cidade " + ((i % 3) + 1));
            doc.setUf("SP");
            doc.setCep("0100" + i + "-000");
            doc.setLatitudeLongitude("-23.5" + i + ", -46.6" + i);
            doc.setSiteId("SITE-" + i);
            doc.setEnderecoId("END-" + i);
            doc.setFaseProjeto("Fase " + ((i % 4) + 1));
            doc.setTipoAtividade("Atividade " + ((i % 3) + 1));
            doc.setDataAgendamento("2026-02-" + String.format("%02d", (i % 28) + 1));
            doc.setHorarioAgendamento("0" + (i % 9) + ":30");
            doc.setNomeClienteFinal("Contato " + i);
            doc.setContatoClienteFinal("(11) 9" + String.format("%03d", i) + "-0000");
            doc.setEmailClienteFinal("cliente" + i + "@example.com");
            doc.setMaterialUtilizado("Material " + i);
            doc.setObservacao1("Observacao " + i);
            doc.setObservacao2("Observacao extra " + i);
            documentacoes.add(doc);
        }
        documentacaoRepository.saveAll(documentacoes);

        List<Faturamento> faturamentos = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Faturamento faturamento = new Faturamento();
            faturamento.setNomeRazaoSocial("Empresa " + i);
            faturamento.setCnpjCpf("00.000.000/000" + i + "-00");
            faturamento.setEnderecoCobranca("Av. Financeira " + i);
            faturamento.setTelefone("(11) 4002-00" + String.format("%02d", i));
            faturamento.setEmail("financeiro" + i + "@example.com");
            faturamento.setDataEmissao("2026-02-" + String.format("%02d", (i % 28) + 1));
            faturamento.setDataVencimento("2026-03-" + String.format("%02d", (i % 28) + 1));
            faturamento.setDataInicio("2026-01-" + String.format("%02d", (i % 28) + 1));
            faturamento.setDataTermino("2026-12-" + String.format("%02d", (i % 28) + 1));
            faturamento.setMesCompetencia("2026-" + String.format("%02d", (i % 12) + 1));
            faturamento.setValorTotal("15000." + String.format("%02d", i));
            faturamento.setGestorClienteFinal("Gestor " + i);
            faturamento.setProjetoTipo("Tipo Projeto " + ((i % 3) + 1));
            faturamento.setProjetoNumero("PRJ-" + String.format("%04d", i));
            faturamento.setOc("OC-" + String.format("%03d", i));
            faturamento.setOs("OS-" + String.format("%03d", i));
            faturamento.setPo("PO-" + String.format("%03d", i));
            faturamento.setClienteFinal("Cliente Final " + i);
            faturamento.setDescricao("Servico " + i);
            faturamento.setValorFreelancer("1200." + String.format("%02d", i));
            faturamento.setValorMateriais("800." + String.format("%02d", i));
            faturamento.setValorFrete("300." + String.format("%02d", i));
            faturamento.setValorDiaria("500." + String.format("%02d", i));
            faturamento.setQuantidadeTecnicos(String.valueOf((i % 5) + 1));
            faturamento.setDuracaoRealAtividade((i + 2) + "h");
            faturamento.setImpostoIss("5%");
            faturamento.setImpostoIcms("12%");
            faturamento.setImpostoPis("1.65%");
            faturamento.setImpostoCofins("7.6%");
            faturamentos.add(faturamento);
        }
        faturamentoRepository.saveAll(faturamentos);

        List<InformacoesCadastroVeiculo> veiculos = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            InformacoesCadastroVeiculo veiculo = new InformacoesCadastroVeiculo();
            veiculo.setPlaca("ABC" + String.format("%04d", i));
            veiculo.setRenavam("REN" + String.format("%06d", i));
            veiculo.setChassi("CHASSI" + String.format("%05d", i));
            veiculo.setTipoVeiculo("Utilitario");
            veiculo.setCategoria("Categoria " + ((i % 3) + 1));
            veiculo.setMarcaModelo("Marca " + ((i % 4) + 1) + " Modelo " + i);
            veiculo.setAnoFabricacao("202" + (i % 5));
            veiculo.setAnoModelo("202" + ((i + 1) % 5));
            veiculo.setCor("Cor " + ((i % 5) + 1));
            veiculo.setCapacidadeCargaPassageiros("1000kg");
            veiculo.setQuilometragemAtual(String.valueOf(10000 + i * 250));
            veiculo.setCrlv("CRLV-" + i);
            veiculo.setSituacaoIpva("Pago");
            veiculo.setVencimentoLicenciamento("2026-11-" + String.format("%02d", (i % 28) + 1));
            veiculo.setSituacaoMultas("Sem multas");
            veiculo.setSeguroObrigatorioDpvatESeguroPrivado("DPVAT ativo");
            veiculo.setDataVencimentoSeguro("2026-10-" + String.format("%02d", (i % 28) + 1));
            veiculo.setEmpresaOuTerceiro("Empresa");
            veiculo.setNomeProprietario("Proprietario " + i);
            veiculo.setCnpjCpf("000.000.000-0" + i);
            veiculo.setHistoricoRevisoes("Revisao " + i);
            veiculo.setProximaRevisaoProgramada("2026-06-" + String.format("%02d", (i % 28) + 1));
            veiculo.setTrocaOleo("2026-04-" + String.format("%02d", (i % 28) + 1));
            veiculo.setManutencaoPneus("Ok");
            veiculo.setRegistroAvariasConsertos("Sem avarias");
            veiculo.setTipoCombustivel("Diesel");
            veiculo.setConsumoMedio("9km/l");
            veiculo.setHistoricoAbastecimento("Registro " + i);
            veiculo.setObservacao1("Obs " + i);
            veiculo.setObservacao2("Obs extra " + i);
            veiculos.add(veiculo);
        }
        informacoesCadastroVeiculoRepository.saveAll(veiculos);

        List<CadastroUsuario> usuarios = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            CadastroUsuario usuario = new CadastroUsuario();
            usuario.setNomeCompleto("Usuario " + i);
            usuario.setRg("RG" + String.format("%06d", i));
            usuario.setOrgaoExpedidor("SSP");
            usuario.setDataExpedicao("201" + (i % 9) + "-01-15");
            usuario.setDataNascimento("199" + (i % 9) + "-05-20");
            usuario.setNomeMae("Mae " + i);
            usuario.setNomePai("Pai " + i);
            usuario.setCpf("000.000.000-0" + i);
            usuario.setNaturalidade("Cidade " + ((i % 4) + 1));
            usuario.setEstadoNasceu("SP");
            usuario.setEndereco("Rua Usuario " + i);
            usuario.setNumero(String.valueOf(10 + i));
            usuario.setComplemento("Casa " + ((i % 3) + 1));
            usuario.setBairro("Bairro " + ((i % 5) + 1));
            usuario.setCidade("Cidade " + ((i % 4) + 1));
            usuario.setUf("SP");
            usuario.setCep("0200" + i + "-000");
            usuario.setLogin("user" + i);
            usuario.setSenha("senha-padrao");
            usuario.setNivelPermissao("OPERADOR");
            usuario.setCargo("Tecnico");
            usuario.setCltOuCnpj("CLT");
            usuario.setDataAdmissao("202" + (i % 5) + "-02-01");
            usuario.setAsoValidade("2026-12-31");
            usuario.setNr10Validade("2026-12-31");
            usuario.setNr35Validade("2026-12-31");
            usuario.setSituacao("Ativo");
            usuario.setObservacao1("Obs " + i);
            usuario.setObservacao2("Obs extra " + i);
            usuario.setObservacao3("Obs final " + i);
            usuarios.add(usuario);
        }
        cadastroUsuarioRepository.saveAll(usuarios);

        List<SiteClaro> sitesClaro = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            SiteClaro site = new SiteClaro();
            site.setSiteId("CLARO-" + i);
            site.setNome("Claro Site " + i);
            site.setAnatelTx("TX-" + i);
            site.setAnatelRf("RF-" + i);
            site.setRedeAnatel("Rede " + ((i % 3) + 1));
            site.setElevacao("100" + i);
            site.setLatitude("-23.6" + i);
            site.setLongitude("-46.7" + i);
            site.setLatitudeDms("23°" + i + "'S");
            site.setLongitudeDms("46°" + i + "'W");
            site.setClusterGeo("Cluster " + ((i % 3) + 1));
            site.setDatum("SIRGAS 2000");
            site.setIbge("IBGE" + i);
            site.setSlaInfra("SLA-" + i);
            site.setClassificacaoOmr("OMR-" + i);
            site.setClassContrato("Contrato " + ((i % 2) + 1));
            site.setLicencasGsmUmtsLte("GSM/UMTS/LTE");
            site.setObservacao1("Obs " + i);
            site.setObservacao2("Obs extra " + i);
            site.setObservacao3("Obs final " + i);
            sitesClaro.add(site);
        }
        siteClaroRepository.saveAll(sitesClaro);

        List<SiteTim> sitesTim = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            SiteTim site = new SiteTim();
            site.setSiteId("TIM-" + i);
            site.setTipoElemento("Elemento " + i);
            site.setTecnologia("4G");
            site.setTipoConexao("Fibra");
            site.setClassificacao("Class " + ((i % 3) + 1));
            site.setDataAquisicao("201" + (i % 9) + "-03-10");
            site.setDataConstrucao("201" + (i % 9) + "-05-15");
            site.setDataAtivacao("2020-01-0" + ((i % 9) + 1));
            site.setDataDesativacao("");
            site.setJustificativa("Sem justificativa");
            site.setTipoTorre("Torre " + ((i % 3) + 1));
            site.setAevNominal("AEV-" + i);
            site.setAlturaEstrutura("30m");
            site.setStatusAtualizacaoSpazio("Atualizado");
            sitesTim.add(site);
        }
        siteTimRepository.saveAll(sitesTim);

        List<SiteVivo> sitesVivo = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            SiteVivo site = new SiteVivo();
            site.setSequencial(String.valueOf(1000 + i));
            site.setSiglaUf("SP");
            site.setUf("SP");
            site.setNome("Vivo Site " + i);
            site.setSigla("VIV" + i);
            site.setSiglaGvOiFixa("GV");
            site.setEnderecoSsi("End SSI " + i);
            site.setAltitude("800");
            site.setPendenciaManutencao("Nenhuma");
            site.setAreaTerceiros("Area " + ((i % 3) + 1));
            site.setEstrutura("Estrutura " + i);
            site.setSiteVip(i % 2 == 0 ? "Sim" : "Nao");
            site.setObservacao1("Obs " + i);
            site.setObservacao2("Obs extra " + i);
            site.setObservacao3("Obs final " + i);
            sitesVivo.add(site);
        }
        siteVivoRepository.saveAll(sitesVivo);

        List<CadastroContrato> contratos = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            CadastroContrato contrato = new CadastroContrato();
            contrato.setClienteDireto("Cliente Direto " + i);
            contrato.setGestorClienteDireto("Gestor Direto " + i);
            contrato.setClienteFinal("Cliente Final " + i);
            contrato.setGestorClienteFinal("Gestor Final " + i);
            contrato.setProjetoTipo("Tipo Projeto " + ((i % 3) + 1));
            contrato.setProjetoNumero("PRJ-" + String.format("%04d", i));
            contrato.setOc("OC-" + String.format("%03d", i));
            contrato.setOs("OS-" + String.format("%03d", i));
            contrato.setPo("PO-" + String.format("%03d", i));
            contrato.setSiteId("SITE-" + i);
            contrato.setEnderecoId("END-" + i);
            contrato.setValorTotalProjeto("25000." + String.format("%02d", i));
            contrato.setFasesProjeto("Fase " + ((i % 4) + 1));
            contratos.add(contrato);
        }
        cadastroContratoRepository.saveAll(contratos);

        List<Chamado> chamados = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Chamado chamado = new Chamado();
            chamado.setNumeroChamado("CH-" + String.format("%05d", i));
            chamado.setClienteDireto("Cliente Direto " + i);
            chamado.setGestorClienteDireto("Gestor Direto " + i);
            chamado.setClienteFinal("Cliente Final " + i);
            chamado.setGestorClienteFinal("Gestor Final " + i);
            chamado.setProjetoTipo("Tipo Projeto " + ((i % 3) + 1));
            chamado.setProjetoNumero("PRJ-" + String.format("%04d", i));
            chamado.setOc("OC-" + String.format("%03d", i));
            chamado.setOs("OS-" + String.format("%03d", i));
            chamado.setPo("PO-" + String.format("%03d", i));
            chamado.setEndereco("Rua Chamado " + i);
            chamado.setNumero(String.valueOf(200 + i));
            chamado.setComplemento("Comp " + ((i % 3) + 1));
            chamado.setBairro("Bairro " + ((i % 4) + 1));
            chamado.setCidade("Cidade " + ((i % 3) + 1));
            chamado.setUf("SP");
            chamado.setCep("0300" + i + "-000");
            chamado.setLatitudeLongitude("-23.7" + i + ", -46.8" + i);
            chamado.setSiteTipo("Tipo Site " + ((i % 3) + 1));
            chamado.setLiberacaoAcessoNumero("LA-" + String.format("%04d", i));
            chamado.setTbsaId("TBSA-" + String.format("%03d", i));
            chamado.setTbsaChamado("TBSA-CH-" + String.format("%03d", i));
            chamado.setDescricaoAtividade("Descricao atividade " + i);
            chamados.add(chamado);
        }
        chamadoRepository.saveAll(chamados);

        System.out.println("Owners loaded: " + ownerRepository.count());
        System.out.println("Projects loaded: " + projectRepository.count());
        System.out.println("Addresses loaded: " + addressRepository.count());
        System.out.println("Files loaded: " + fileRepository.count());
        System.out.println("Documentacoes loaded: " + documentacaoRepository.count());
        System.out.println("Faturamentos loaded: " + faturamentoRepository.count());
        System.out.println("InformacoesCadastroVeiculo loaded: " + informacoesCadastroVeiculoRepository.count());
        System.out.println("CadastroUsuarios loaded: " + cadastroUsuarioRepository.count());
        System.out.println("SitesClaro loaded: " + siteClaroRepository.count());
        System.out.println("SitesTim loaded: " + siteTimRepository.count());
        System.out.println("SitesVivo loaded: " + siteVivoRepository.count());
        System.out.println("CadastroContratos loaded: " + cadastroContratoRepository.count());
        System.out.println("Chamados loaded: " + chamadoRepository.count());
        System.out.println("BootstrapData completed.");
    }

    private String slugify(String value) {
        return value.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replace(" ", "-");
    }

}
