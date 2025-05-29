import React, { useState, useEffect, useCallback } from 'react';
import { Card, Row, Col, Table, Form } from 'react-bootstrap';
import { Line } from 'react-chartjs-2';
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
} from 'chart.js';
import axios from 'axios';
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import { formatMoney } from '../utils/format';

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
);

export default function Dashboard() {
    const [resumo, setResumo] = useState({
        totalReceitas: 0,
        totalDespesas: 0,
        saldo: 0
    });
    const [ultimasTransacoes, setUltimasTransacoes] = useState([]);
    const [grafico, setGrafico] = useState({
        labels: [],
        datasets: []
    });
    const [mes, setMes] = useState(new Date().getMonth() + 1);
    const [ano, setAno] = useState(new Date().getFullYear());

    const carregarDados = useCallback(async () => {
        try {
            const primeiroDia = new Date(ano, mes - 1, 1);
            const ultimoDia = new Date(ano, mes, 0);

            const response = await axios.get('http://localhost:8080/api/transacoes', {
                params: {
                    inicio: format(primeiroDia, 'yyyy-MM-dd'),
                    fim: format(ultimoDia, 'yyyy-MM-dd'),
                    orderBy: 'dataTransacao',
                    direction: 'DESC'
                }
            });
            const transacoes = response.data;

            const totalReceitas = transacoes
                .filter(t => t.tipo === 'RECEITA')
                .reduce((acc, t) => acc + Number(t.valor), 0);

            const totalDespesas = transacoes
                .filter(t => t.tipo === 'DESPESA')
                .reduce((acc, t) => acc + Number(t.valor), 0);

            setResumo({
                totalReceitas,
                totalDespesas,
                saldo: totalReceitas - totalDespesas
            });

            const responseLatest = await axios.get('http://localhost:8080/api/transacoes', {
                params: {
                    inicio: format(primeiroDia, 'yyyy-MM-dd'),
                    fim: format(ultimoDia, 'yyyy-MM-dd'),
                    limit: 5,
                    orderBy: 'dataTransacao',
                    direction: 'DESC'
                }
            });
            setUltimasTransacoes(responseLatest.data);

            const diasNoMes = new Date(ano, mes, 0).getDate();
            const dias = Array.from({ length: diasNoMes }, (_, i) => i + 1);
            
            const receitasPorDia = dias.map(dia => {
                return transacoes
                    .filter(t => t.tipo === 'RECEITA' && new Date(t.dataTransacao).getDate() === dia)
                    .reduce((acc, t) => acc + Number(t.valor), 0);
            });

            const despesasPorDia = dias.map(dia => {
                return transacoes
                    .filter(t => t.tipo === 'DESPESA' && new Date(t.dataTransacao).getDate() === dia)
                    .reduce((acc, t) => acc + Number(t.valor), 0);
            });

            setGrafico({
                labels: dias,
                datasets: [
                    {
                        label: 'Receitas',
                        data: receitasPorDia,
                        borderColor: '#28a745',
                        backgroundColor: 'rgba(40, 167, 69, 0.1)',
                        tension: 0.4
                    },
                    {
                        label: 'Despesas',
                        data: despesasPorDia,
                        borderColor: '#dc3545',
                        backgroundColor: 'rgba(220, 53, 69, 0.1)',
                        tension: 0.4
                    }
                ]
            });
        } catch (error) {
            console.error('Erro ao carregar dados:', error);
        }
    }, [mes, ano]);

    useEffect(() => {
        carregarDados();
    }, [mes, ano, carregarDados]);

    const meses = [
        'Janeiro',
        'Fevereiro',
        'Março',
        'Abril',
        'Maio',
        'Junho',
        'Julho',
        'Agosto',
        'Setembro',
        'Outubro',
        'Novembro',
        'Dezembro'
    ];

    return (
        <div className="container-fluid px-4">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2>Dashboard</h2>
                <div className="d-flex gap-3">
                    <Form.Select
                        value={mes}
                        onChange={(e) => setMes(Number(e.target.value))}
                        style={{ width: '150px' }}
                    >
                        {meses.map((nome, index) => (
                            <option key={`${nome}-${index}`} value={index + 1}>
                                {nome}
                            </option>
                        ))}
                    </Form.Select>
                    <Form.Select
                        value={ano}
                        onChange={(e) => setAno(Number(e.target.value))}
                        style={{ width: '120px' }}
                    >
                        {Array.from({ length: 5 }, (_, i) => new Date().getFullYear() - i).map(ano => (
                            <option key={ano} value={ano}>
                                {ano}
                            </option>
                        ))}
                    </Form.Select>
                </div>
            </div>

            <Row className="mb-4">
                <Col md={4}>
                    <Card className="h-100">
                        <Card.Body>
                            <Card.Title>Receitas</Card.Title>
                            <h3 className="text-success mb-0">R$ {formatMoney(resumo.totalReceitas)}</h3>
                        </Card.Body>
                    </Card>
                </Col>
                <Col md={4}>
                    <Card className="h-100">
                        <Card.Body>
                            <Card.Title>Despesas</Card.Title>
                            <h3 className="text-danger mb-0">R$ {formatMoney(resumo.totalDespesas)}</h3>
                        </Card.Body>
                    </Card>
                </Col>
                <Col md={4}>
                    <Card className="h-100">
                        <Card.Body>
                            <Card.Title>Saldo</Card.Title>
                            <h3 className={resumo.saldo >= 0 ? 'text-success' : 'text-danger'}>
                                R$ {formatMoney(resumo.saldo)}
                            </h3>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            <Card className="mb-4">
                <Card.Body>
                    <Card.Title>Evolução do Mês</Card.Title>
                    <div style={{ height: '300px' }}>
                        <Line
                            data={grafico}
                            options={{
                                responsive: true,
                                maintainAspectRatio: false,
                                plugins: {
                                    legend: {
                                        position: 'top',
                                    },
                                    title: {
                                        display: false
                                    }
                                },
                                scales: {
                                    y: {
                                        beginAtZero: true,
                                        ticks: {
                                            callback: function(value) {
                                                return 'R$ ' + formatMoney(value);
                                            }
                                        }
                                    }
                                }
                            }}
                        />
                    </div>
                </Card.Body>
            </Card>

            <Card>
                <Card.Body>
                    <Card.Title>Últimas Transações</Card.Title>
                    <Table hover>
                        <thead>
                            <tr>
                                <th>Data</th>
                                <th>Descrição</th>
                                <th>Categoria</th>
                                <th>Valor</th>
                                <th>Tipo</th>
                            </tr>
                        </thead>
                        <tbody>
                            {ultimasTransacoes.map(transacao => (
                                <tr key={transacao.id}>
                                    <td>{format(new Date(transacao.dataTransacao), 'dd/MM/yyyy', { locale: ptBR })}</td>
                                    <td>{transacao.descricao}</td>
                                    <td>{transacao.categoriaNome}</td>
                                    <td className={transacao.tipo === 'RECEITA' ? 'text-success' : 'text-danger'}>
                                        R$ {formatMoney(transacao.valor)}
                                    </td>
                                    <td>
                                        <span className={transacao.tipo === 'RECEITA' ? 'text-success' : 'text-danger'}>
                                            {transacao.tipo === 'RECEITA' ? 'Receita' : 'Despesa'}
                                        </span>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </Table>
                </Card.Body>
            </Card>
        </div>
    );
} 