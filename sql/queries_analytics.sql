-- 10 Operadoras com Maiores Despesas no Último Trimestre (4T2024)
SELECT 
    COALESCE(o.razao_social, 'OPERADORA NÃO CADASTRADA (' || d.reg_ans || ')') AS razao_social,
    d.reg_ans,
    SUM(REPLACE(d.vl_saldo_final, ',', '.')::NUMERIC) AS total_despesas
FROM demonstracoes_contabeis d
LEFT JOIN operadoras_ativas o ON d.reg_ans = o.registro_ans
WHERE d.data BETWEEN '2024-10-01' AND '2024-12-31'
AND d.descricao = 'SINISTROS CONHECIDOS' AND d.descricao = 'EVENTOS' AND d.descricao = 'AVISADOS DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR' 
GROUP BY d.reg_ans, o.razao_social
ORDER BY total_despesas DESC
LIMIT 10;

-- 10 Operadoras com Maiores Despesas no Último Ano (2024)
SELECT 
    COALESCE(o.razao_social, 'OPERADORA NÃO CADASTRADA (' || d.reg_ans || ')') AS razao_social,
    d.reg_ans,
    SUM(REPLACE(d.vl_saldo_final, ',', '.')::NUMERIC) AS total_despesas
FROM demonstracoes_contabeis d
LEFT JOIN operadoras_ativas o ON d.reg_ans = o.registro_ans
WHERE d.data BETWEEN '2024-01-01' AND '2024-12-31'
AND d.descricao = 'SINISTROS CONHECIDOS' AND d.descricao = 'EVENTOS' AND d.descricao = 'AVISADOS DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR' 
GROUP BY d.reg_ans, o.razao_social
ORDER BY total_despesas DESC
LIMIT 10;