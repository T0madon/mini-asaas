USE asaasdevelopment;
-- 1)Select de cobranças (payment) com valor maior que R$ 1.000,00
SELECT * FROM payment
WHERE value > 1000;

-- 2)Soma dos valores de cobranças recebidas com a data de criação maior que 2020, agrupadas por tipo de cobrança (billing_type)
SELECT billing_type, SUM(value) AS valor_recebido FROM payment
WHERE YEAR(date_created) > 2020 AND status='RECEIVED'
GROUP BY billing_type;

-- 3)Lista de 20 pagadores (customer_account) com valores recebidos maiores que R$ 1.000,00
SELECT ca.name, SUM(p.value) as valor_total_recebido
FROM customer_account ca
INNER JOIN payment p ON ca.id = p.customer_account_id
WHERE p.status = 'RECEIVED'
GROUP BY ca.name
HAVING SUM(p.value) > 1000
ORDER BY SUM(p.value) DESC
LIMIT 20;

-- 4)Primeiro nome dos clientes (customer) com análise geral aprovada (customer_regiter_status), ordenado por data de aprovação
SELECT SUBSTRING_INDEX(c.name, ' ', 1) AS primeiro_nome, crs.last_general_status_change as ultima_alteracao_data
FROM customer c
INNER JOIN customer_register_status crs ON c.id = crs.customer_id
WHERE crs.general_approval='APPROVED'
ORDER BY crs.last_general_status_change DESC;