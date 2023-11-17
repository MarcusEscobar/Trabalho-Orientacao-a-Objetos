package view;

import java.util.*;


import youtube.*;
import dados.*;

public abstract class Menu {


    public static void clear(){
        System.out.println("\033c");
    }

    public static void menu1(Dados dados, Scanner entrada){
        clear();
        System.out.println("Bem-Vindo ao Youtube. Faça seu cadastro\n");
        System.out.println("Insira um nome");
        String nome = entrada.nextLine();
        System.out.println(" ");
        System.out.println("Insira uma senha");
        String senha = entrada.nextLine();
        System.out.println(" ");
        Usuario user = new Usuario(senha, nome);
        dados.setUsuario(user);
        clear();
        menu2(dados, entrada);
    }

    public static void menu2(Dados dados, Scanner entrada){
        System.out.println("Olá "+dados.getUsuario().getNomeUsuario()+", o que deseja?\n");
        String opçoes = new String("Escolha uma opção:\n\n");
        opçoes += "  0 - Sair\n";
        opçoes += "  1 - Listar canais\n";
        opçoes += "  2 - Canais Inscritos\n";
        opçoes += "  3 - histórico\n";
        opçoes += "  4 - Editar usuario\n";
        opçoes += "  5 - Criar canal\n";
        System.out.println(opçoes);
        int valor = entrada.nextInt();
        System.out.println(" ");//Pular uma linha
        switch (valor) {
            case 0:
                clear();
                System.out.println("Obrigado por usar o Youtube!!");
                break;
            case 1:
                clear();
                menu3_Canais(dados, entrada);
                break;
            case 2:
                clear();
                menu3_Inscricoes(dados, entrada);
                break;

            case 3:
                clear();
                dados.getUsuario().setVideoHistorico(0, dados.getCanal(0).getVideo(0));
                dados.getUsuario().setQtdHistorico(1);
                menu5_Historico(dados.getUsuario(), dados, entrada);
                break;   
            default:
                clear();
                System.out.println("Opção inválida\n");
                menu2(dados, entrada);//Recursividade, simulando loop de repetição
                break;
        }
    }

    public static void menu3_Canais(Dados dados, Scanner entrada){
        String opçoes = new String("Escolha uma opção:\n\n");
        opçoes += "  0 - voltar\n\n";
            opçoes +="Canais disponíveis:\n\n";
            for(int i = 1; i<= dados.getQtdCanais(); i++){
                String s = String.valueOf(i);//Transformo j em string
                opçoes += new String("  "+s+" - "+dados.getCanal(i-1).getNomeCanal()+"\n");
            }
        System.out.println(opçoes);
        int valor = entrada.nextInt();
        if(valor == 0){
            clear();
            menu2(dados, entrada);
        }else if(valor >= 1 && valor <= dados.getQtdCanais()){
            clear();
            menu4(dados, entrada, dados.getCanal(valor-1));

        }else{
            System.out.println("Opção inválida");
            clear();
            menu3_Canais(dados, entrada);
        }
    }
    
    public static void menu3_Inscricoes(Dados dados, Scanner entrada){
        String opçoes = new String("Escolha uma opção:\n\n");
        opçoes += "  0 - voltar\n\n";
        if(dados.getUsuario().getQtdInscricoes() == 0){
            opçoes+= "Você não está inscrito em nenhum canal!\n";
        }else{
            opçoes +="Canais disponíveis:\n\n";
            for(int i = 1; i<= dados.getUsuario().getQtdInscricoes(); i++){
                String s = String.valueOf(i);//Transformo j em string
                opçoes += new String("  "+s+" - "+dados.getUsuario().getInscricao(i-1).getNomeCanal()+"\n");
            }
        }
        System.out.println(opçoes);
        int valor = entrada.nextInt();
        if(valor == 0){
            clear();
            menu2(dados, entrada);
        }else if(valor >= 1 && valor <= dados.getUsuario().getQtdInscricoes() && dados.getUsuario().getQtdInscricoes() != 0){
            clear();
            menu4(dados, entrada, dados.getUsuario().getInscricao(valor-1));

        }else{
            System.out.println("Opção inválida");
            clear();
            menu3_Inscricoes(dados, entrada);
        }


    }

    public static void menu4(Dados dados, Scanner entrada, Canal canal){
        System.out.println("Este é o canal: "+canal.getNomeCanal());
        String opçoes = new String("Escolha uma opção:\n\n");
        opçoes += "  0 - voltar para pagina principal\n";
        if(dados.getUsuario().inscricoesToString().contains(canal.getNomeCanal())){
            //está inscrito
            opçoes += "  1 - Cancelar inscrição\n";
        }else{
            opçoes += "  1 - Inscrever-se\n";
        }
        opçoes += "  2 - Menu de vídeos\n";
        opçoes += "  3 - Menu enquetes\n";
        opçoes += "  4 - Editar canal\n";
        opçoes += "  5 - Excluir canal\n";
        opçoes += "  6 - Informações do canal\n";
        System.out.println(opçoes);
        int valor = entrada.nextInt();
        switch (valor) {
            case 0:
                clear();
                menu2(dados, entrada);
                break;
            case 1:
                if(dados.getUsuario().inscricoesToString().contains(canal.getNomeCanal())){
                //está inscrito
                    dados.getUsuario().CancelarInscrição(canal);
                    canal.setQtdInscritos(canal.getQtdInscritos()-1);
                    clear();
                     menu4(dados, entrada, canal);
                }else{
                    dados.getUsuario().inscreverSe(canal);
                    canal.setQtdInscritos(canal.getQtdInscritos()+1);
                    clear();
                    menu4(dados, entrada, canal);

                }
                break;
            case 2:
                clear();
               //menu5_Videos(canal, dados, entrada);
                menuDeVideos(canal, dados, entrada);
                break;
            case 3:
                clear();
                menu5_Enquete(canal, dados, entrada);
                break;
            case 4:
                //editar canal
                clear();
                EditarCanal(canal, dados, entrada);
                break;
            case 6:
                clear();
                InfoDoCanal(canal, dados, entrada);
                break;
            default:
                clear();
                System.out.println("opção inválida");
                menu4(dados, entrada, canal);
                break;
        }
            
    }

    public static void EditarCanal(Canal canal, Dados dados, Scanner entrada){
        entrada.nextLine();
        System.out.println("Insira o novo nome do canal:\n");
        String novoNome = entrada.nextLine();
        System.out.println(" ");
        System.out.println("Nome alterado com sucesso.");
        canal.setNomeCanal(novoNome);
        clear();
        menu4(dados, entrada, canal);
    }

    public static void InfoDoCanal(Canal canal, Dados dados, Scanner entrada){
        System.out.println("Informações do canal "+canal.getNomeCanal()+":\n");
        System.out.println("Proprietario: "+canal.getProprietario().getNomeUsuario()+"\n");
        System.out.println("Quantidade de inscritos: "+canal.getQtdInscritos()+"\n");
        System.out.println("Quantidade de videos: "+canal.getQtdVideos()+"\n");
        System.out.println("Quantidade de enquetes: "+canal.getQtdEnquetes()+"\n");
        System.out.println("Total de visualizações no canal: "+canal.getTotalVisualizacoes()+"\n");

        String opçoes = new String("0 - Voltar ao canal\n\n");
        System.out.println(opçoes);
        int valor = entrada.nextInt();
        switch (valor) {
            case 0:
                menu4(dados, entrada, canal);
                break;
        
            default:
                menu4(dados, entrada, canal);
                break;
        }


    }

    public static void menuExcluirCanal(Canal canal, Dados dados, Scanner entrada){
        String opçoes = new String("Tem certeza que deseja excluir o canal?\n\n");
        opçoes += "0 - Sim\n";
        opçoes += "1 - Não\n";
        System.out.println(opçoes);
        int valor = entrada.nextInt();
        switch (valor) {
            case 0:
                clear();
                ExcluirCanal(canal, dados);
                menu3_Canais(dados, entrada);
                break;
            case 1:
                clear();
                menu4(dados, entrada, canal);
                break;
            default:
                clear();
                System.out.println("Opção inválida\n");
                menu4(dados, entrada, canal);
                break;
        }


    }

    public static void ExcluirCanal(Canal canal, Dados dados){


    }


    public static void menuDeVideos(Canal canal, Dados dados, Scanner entrada){
        String opçoes = new String("Escolha uma opção\n\n");
        opçoes += "0 - Voltar ao canal\n";//menu4
        opçoes += "1 - Criar video\n";
        opçoes += "2 - Listar todos os vídeos\n";//Menu5video
        opçoes += "3 - Buscar video pelo título\n";
        System.out.println(opçoes);
        int valor = entrada.nextInt();
        switch (valor) {
            case 0:
                clear();
                menu4(dados, entrada,canal);
                break;
            case 1:
                //Create video
                break;
            case 2:
                menu5_Videos(canal, dados, entrada);
                break;
            case 3:
                //Buscar por titulo
                clear();
                break;
            default:
                clear();
                System.out.println("opção inválida");
                menuDeVideos(canal, dados, entrada);
                break;
        }

    }


    

    public static void menu5_Videos(Canal canal, Dados dados, Scanner entrada){//Printa Videos Canal
        //Criar toString video
        String opçoes = new String("Escolha uma opção\n\n");
        opçoes +="  0 - voltar ao canal\n\n";
        opçoes+="Videos de "+canal.getNomeCanal()+"\n\n";       
        for(int i = 1; i<= canal.getQtdVideos(); i++){
            String s = String.valueOf(i);//Transformo j em string
            opçoes += new String("  "+s+" - "+canal.getVideo(i-1).getTitulo()+"\n");
        }System.out.println(opçoes);
        int valor = entrada.nextInt();
        if(valor == 0){
            clear();
            menu4(dados, entrada,canal);
        }else if(valor >= 1 && valor <= canal.getQtdVideos()){
            clear();
            menu6(canal.getVideo(valor-1), dados, entrada);

        }else{
            clear();
            System.out.println("Opção inválida");
            menu5_Videos(canal, dados, entrada);
        }
    }
    
    public static void menu5_Historico(Usuario user, Dados dados, Scanner entrada){//Printa Histórico
        String opçoes = new String("Escolha uma opção\n\n");
        opçoes +="  0 - Voltar para pagina principal\n\n";
        opçoes+="Histórico de "+user.getNomeUsuario()+":\n\n";
        if(user.getQtdHistorico() == 0 ){
            opçoes +="Você não possui vídeos em seu histórico.\n";
        }else{
            for(int i = 1; i <= user.getQtdHistorico(); i++){
                String s = String.valueOf(i);//Transformo j em string
                opçoes += new String("  "+s+" - "+user.getVideoHistorico(i-1).getTitulo()+"\n");//do canal tal?(autor)
            }
        }     
        System.out.println(opçoes);
        int valor = entrada.nextInt();
        if(valor == 0){
            clear();
            menu2(dados, entrada);
        }else if(valor >= 1 && valor <= user.getQtdHistorico() && user.getQtdHistorico()!=0){
            clear();
            //Menu 6
        }else{
            System.out.println("Opção inválida");
            clear();
            menu5_Historico(user, dados, entrada);
        }
    }
    
    public static void menu5_Enquete(Canal canal, Dados dados, Scanner entrada){
        String opçoes = new String("Escolha um opção\n\n");
        opçoes +="  0 - voltar ao canal\n\n";
        opçoes+="Enquetes de "+canal.getNomeCanal()+"\n\n";       
        for(int i = 1; i<= canal.getQtdEnquetes(); i++){
            String s = String.valueOf(i);//Transformo j em string
            opçoes += new String("  "+s+" - "+canal.getEnquete(i-1).getPergunta()+"\n");
        }System.out.println(opçoes);
        int valor = entrada.nextInt();
        if(valor == 0){
            clear();
            menu4(dados, entrada,canal);
        }else if(valor >= 1 && valor <= canal.getQtdEnquetes()){
            clear();
            //Menu8

        }else{
            clear();
            System.out.println("Opção inválida");
            menu5_Enquete(canal, dados, entrada);
        }

    }

    public static void menu6(Video video, Dados dados, Scanner entrada){
        if(video.getIsPausado()){
            System.out.println("O video está pausado\n");
        }else{
            System.out.println("O video "+video.getTitulo()+" está passando\n");
        }
        switch (video.getVelocidade()) {
            case 2:
                System.out.println("Velocidade atual: 2x\n");
                break;
        
            default:
                System.out.println("Velocidade atual: 1x\n");
                break;
        }
        System.out.println("Quantidade Gostei: "+video.getQtdGostei()+"\n");
        System.out.println("Quantidade Não Gostei: "+video.getQtdNaoGostei()+"\n");
        String opcoes = new String("Escolha uma opção\n\n");
        opcoes += "  0 - voltar canal\n";
        opcoes += "  1 - voltar página principal\n";
        if(!video.getStatusGostei() && !video.getStatusNãoGostei()){
            opcoes += "  2 - Gostei\n"; //GOSTEI = TRUE // NAO GOSTEI = FALSE
            opcoes += "  3 - Não gostei\n";//GOSTEI = FALSE // NAO GOSTEI = TRUE
        }else if(video.getStatusGostei() == true && video.getStatusNãoGostei() == false){
            opcoes += "  3 - Não gostei\n";
        }else{
            opcoes += "  2 - Gostei\n";}
        opcoes += "  4 - Ler comentários\n";
        if(video.getIsPausado()){
             opcoes += "  5 - Reproduzir\n";
        }else{
            opcoes += "  5 - pausar video\n";
        }
        switch (video.getVelocidade()){
            case 2:
                opcoes += "  6 - Reduzir velocidade\n";
                break;
        
            default:
                opcoes += "  6 - aumentar velocidade\n";
                break;
        }
        System.out.println(opcoes);
        int valor = entrada.nextInt();
        switch (valor) {
            case 0:
                clear();
                menu4(dados, entrada, video.getAutor());
                break;

            case 1:
                clear();
                menu2(dados, entrada);
                break;
            case 2:
                video.adicionarGostei();
                clear();
                menu6(video, dados, entrada);
                break;
            case 3:
                video.adicionarNaoGostei();
                clear();
                menu6(video, dados, entrada);
                break;
            case 4:
                clear();
                lerComentarios(dados, entrada, video);
                break;
            case 5:
                if(video.getIsPausado()){
                    video.setIsPausado(false);
                    clear();
                    menu6(video, dados, entrada);
                }else{
                    video.setIsPausado(true);
                    clear();
                    menu6(video, dados, entrada);}
                break;
            case 6:
                if(video.getVelocidade() == 1){
                    video.setVelocidade(2);
                    clear();
                    menu6(video, dados, entrada);
                }else{
                    video.setVelocidade(1);
                    clear();
                    menu6(video, dados, entrada);}
                break;
            default:
                clear();
                System.out.println("Opção inválida");
                menu6(video, dados, entrada);
                break;
        }
    }
    
    public static void lerComentarios(Dados dados, Scanner entrada, Video video){
        String opcao = new String("Escolha uma opção\n\n");
        opcao += "  0 - Retornar ao video\n\n";
        opcao +="Comentários de "+video.getTitulo()+" do canal "+video.getAutor().getNomeCanal()+"\n";
        for(int i = 1; i <= video.getQtdComentarios();i++){
            String s = String.valueOf(i);
            opcao += new String("  "+s+" - "+video.getComentario(i-1).comentarioToString()+"\n");
        }
        System.out.println(opcao);
        int valor = entrada.nextInt();
        if(valor == 0){
            clear();
            menu6(video, dados, entrada);
        }else if(valor >=1 && valor <=video.getQtdComentarios()){
            clear();
            //menu7
        }else{
            clear();
            System.out.println("Opção inválida");
            lerComentarios(dados, entrada, video);
        }

    }
}