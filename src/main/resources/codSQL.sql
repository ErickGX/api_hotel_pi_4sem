-- constraints
    CONSTRAINT chk_cpf_cnpj CHECK (
        (tipo_cliente = 'FISICO' AND cpf IS NOT NULL AND cnpj IS NULL)
        OR
        (tipo_cliente = 'JURIDICO' AND cnpj IS NOT NULL AND cpf IS NULL)
    ) -- possivel verificacao pelo banco de dados para tipos de clientes