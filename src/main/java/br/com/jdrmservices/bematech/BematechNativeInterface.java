/*package br.com.jdrmservices.bematech;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface BematechNativeInterface extends Library {

	public BematechNativeInterface Instance = (BematechNativeInterface) 
            Native.loadLibrary("C:\\PDV\\ext\\mp2064.dll", BematechNativeInterface.class);
    
    public int ConfiguraModeloImpressora(int modelo);
    public int IniciaPorta(String porta);
    public int FormataTX(String BufTras, int tipoletra, int italic, int sublin, int expand, int enfat);
    public int ImprimeBitmap(String bitmap, int orientacao);
    public int AcionaGuilhotina(int cut);
    public int AjustaLarguraPapel(int largura);
    public int BematechTX(String sTexto);
    public int ComandoTX(String sComando, int sTamanho);
}*/