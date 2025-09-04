package com.pi.senac.Hotel4ma.businessRule;


import com.pi.senac.Hotel4ma.model.Pagamento;
import org.springframework.stereotype.Service;

@Service
public class PagamentoDescAVista {

    public Double calcularValorComDesconto(Pagamento pagamento) {
        if ("Dinheiro".equalsIgnoreCase(pagamento.getFormaPagamento())
                || "Pix".equalsIgnoreCase(pagamento.getFormaPagamento())) {
            return pagamento.getValor() * 0.90; // 10% de desconto
        }
        return pagamento.getValor();
    }
}
