package youtube;

public class Usuario {
    private String email;
    private String senha;
    private String nomeUsuario;
    private Canal[] inscricoes = new Canal[100];
    private Video[] historico = new Video[100];
    
    public Usuario(String email, String senha, String nomeUsuario){//Falta o atributo autor (rever Tipo)
        this.email = email;
        this.senha = senha;
        this.nomeUsuario = nomeUsuario;
    
    }
//Gets e Sets
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getSenha(){
        return senha;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }
    public String getNomeUsuario(){
        return nomeUsuario;
    }
    public void setNomeUsuario(String nomeUsuario){
        this.nomeUsuario = nomeUsuario;
    }

    public Canal[] getInscricoes(){//Retorna o Array completo
        return inscricoes;
    }
    public Canal getInscricao(int i){//Retorna um elemento do Array com base no indice
        return inscricoes[i];
        /*
         *talvez seja bom criar uma variavel que armazena a quantidade de inscrições, caso o int i(parametro) seja maior que qtdInscrições
         *a função nao retorna nada. Da forma atual é possovel passar qualquer numero inteiro, o que pode ocasionar em um Out of Index. 
        */
    }

    public void setInscricoes(Canal[] inscricoes){//Substitui todo o Array
        this.inscricoes = inscricoes;
    }

    public void setIncricao(int i, Canal c){//Subtitui somente um elemento do Array
        inscricoes[i] = c;
    }

    public Video[] gethistorico(){
        return historico;
    }

    public Video getVideoHistorico(int i){//Semelhante a getIscricao, também recomendado um variavel auxiliar
        return historico[i];
    }

    public void setHistorico(Video[] historico){
        this.historico = historico;
    }

    public void setVideoHistorico(int i, Video v){
        historico[i] = v;
    }
}
/* Os métodos setIscricao e setVideoHistorico podem ser utilizados para inserção de novos elementos nos respectivos Arrays.
 * Só tomar cuidado com a capasidade máxima.
 * Também é possível criar novas funcões de inserção. Atravez da classe ArrayUtils é possivel usar o metodo .add, que recebe
 * um Array e um novo elemento e faz tipo um .append() do Python.
*/
