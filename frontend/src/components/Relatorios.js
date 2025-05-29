import React, { useState, useEffect, useCallback } from 'react'
import { Card, Form, Row, Col, Button } from 'react-bootstrap'
import { Line, Pie } from 'react-chartjs-2'
import axios from 'axios'
import { format } from 'date-fns'
import { formatMoney } from '../utils/format'
import './Relatorios.css'

export default function Relatorios() {
    const [periodo, setPeriodo] = useState({
        inicio: '',
        fim: ''
    })
    const [dados, setDados] = useState({
        receitas: 0,
        despesas: 0,
        saldo: 0,
        receitasPorCategoria: [],
        despesasPorCategoria: [],
        evolucaoMensal: {
            labels: [],
            receitas: [],
            despesas: []
        }
    })

    const carregarDados = useCallback(async () => {
        try {
            const params = {}
            if (periodo.inicio) params.inicio = periodo.inicio
            if (periodo.fim) params.fim = periodo.fim

            const response = await axios.get('http://localhost:8080/api/transacoes', { params })
            const transacoes = response.data
            const receitas = transacoes
                .filter(t => t.tipo === 'RECEITA')
                .reduce((acc, t) => acc + Number(t.valor), 0)
            const despesas = transacoes
                .filter(t => t.tipo === 'DESPESA')
                .reduce((acc, t) => acc + Number(t.valor), 0)

            const categoriasResponse = await axios.get('http://localhost:8080/api/categorias')
            const categorias = categoriasResponse.data

            const receitasPorCategoria = categorias.map(categoria => {
                const valor = transacoes
                    .filter(t => t.tipo === 'RECEITA' && t.categoriaId === categoria.id)
                    .reduce((acc, t) => acc + Number(t.valor), 0)
                return {
                    categoria: categoria.nome,
                    valor
                }
            }).filter(item => item.valor > 0)

            const despesasPorCategoria = categorias.map(categoria => {
                const valor = transacoes
                    .filter(t => t.tipo === 'DESPESA' && t.categoriaId === categoria.id)
                    .reduce((acc, t) => acc + Number(t.valor), 0)
                return {
                    categoria: categoria.nome,
                    valor
                }
            }).filter(item => item.valor > 0)

            const meses = []
            const receitasPorMes = []
            const despesasPorMes = []

            let dataInicio = periodo.inicio ? new Date(periodo.inicio) : 
                new Date(Math.min(...transacoes.map(t => new Date(t.dataTransacao))))
            let dataFim = periodo.fim ? new Date(periodo.fim) : 
                new Date(Math.max(...transacoes.map(t => new Date(t.dataTransacao))))

            let dataAtual = new Date(dataInicio)
            while (dataAtual <= dataFim) {
                const mesAno = format(dataAtual, 'MM/yyyy')
                meses.push(mesAno)

                const receitasMes = transacoes
                    .filter(t => {
                        const dataTransacao = new Date(t.dataTransacao)
                        return t.tipo === 'RECEITA' &&
                                dataTransacao.getMonth() === dataAtual.getMonth() &&
                                dataTransacao.getFullYear() === dataAtual.getFullYear()
                    })
                    .reduce((acc, t) => acc + Number(t.valor), 0)

                const despesasMes = transacoes
                    .filter(t => {
                        const dataTransacao = new Date(t.dataTransacao)
                        return t.tipo === 'DESPESA' &&
                                dataTransacao.getMonth() === dataAtual.getMonth() &&
                                dataTransacao.getFullYear() === dataAtual.getFullYear()
                    })
                    .reduce((acc, t) => acc + Number(t.valor), 0)

                receitasPorMes.push(receitasMes)
                despesasPorMes.push(despesasMes)

                dataAtual.setMonth(dataAtual.getMonth() + 1)
            }

            setDados({
                receitas,
                despesas,
                saldo: receitas - despesas,
                receitasPorCategoria,
                despesasPorCategoria,
                evolucaoMensal: {
                    labels: meses,
                    receitas: receitasPorMes,
                    despesas: despesasPorMes
                }
            })
        } catch (error) {
            console.error('Erro ao carregar dados:', error)
        }
    }, [periodo]);

    useEffect(() => {
        carregarDados()
    }, [periodo, carregarDados]);

    const handlePeriodoChange = (e) => {
        const { name, value } = e.target
        setPeriodo(prev => ({
            ...prev,
            [name]: value
        }))
    }

    const limparFiltros = () => {
        setPeriodo({
            inicio: '',
            fim: ''
        })
    }

    return (
        <div className="relatorios">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2>Relatórios</h2>
                <div className="d-flex gap-3 align-items-center">
                    <Form.Group className="d-flex gap-3">
                        <Form.Control
                            type="date"
                            name="inicio"
                            value={periodo.inicio}
                            onChange={handlePeriodoChange}
                            placeholder="Data Início"
                        />
                        <Form.Control
                            type="date"
                            name="fim"
                            value={periodo.fim}
                            onChange={handlePeriodoChange}
                            placeholder="Data Fim"
                        />
                    </Form.Group>
                    <Button 
                        variant="outline-secondary" 
                        onClick={limparFiltros}
                        title="Limpar filtros de data"
                    >
                        <i className="fas fa-times"></i>
                    </Button>
                </div>
            </div>

            <Row className="mb-4">
                <Col md={4}>
                    <Card className="text-center">
                        <Card.Body>
                            <Card.Title>Total de Receitas</Card.Title>
                            <h3 className="text-success">R$ {formatMoney(dados.receitas)}</h3>
                        </Card.Body>
                    </Card>
                </Col>
                <Col md={4}>
                    <Card className="text-center">
                        <Card.Body>
                            <Card.Title>Total de Despesas</Card.Title>
                            <h3 className="text-danger">R$ {formatMoney(dados.despesas)}</h3>
                        </Card.Body>
                    </Card>
                </Col>
                <Col md={4}>
                    <Card className="text-center">
                        <Card.Body>
                            <Card.Title>Saldo</Card.Title>
                            <h3 className={dados.saldo >= 0 ? 'text-success' : 'text-danger'}>
                                R$ {formatMoney(dados.saldo)}
                            </h3>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            <Row className="mb-4">
                <Col md={6}>
                    <Card>
                        <Card.Body>
                            <Card.Title>Receitas por Categoria</Card.Title>
                            <Pie
                                data={{
                                    labels: dados.receitasPorCategoria.map(item => item.categoria),
                                    datasets: [{
                                        data: dados.receitasPorCategoria.map(item => item.valor),
                                        backgroundColor: [
                                            '#28a745',
                                            '#20c997',
                                            '#17a2b8',
                                            '#0dcaf0',
                                            '#0d6efd'
                                        ]
                                    }]
                                }}
                                options={{
                                    responsive: true,
                                    plugins: {
                                        legend: {
                                            position: 'right'
                                        }
                                    }
                                }}
                            />
                        </Card.Body>
                    </Card>
                </Col>
                <Col md={6}>
                    <Card>
                        <Card.Body>
                            <Card.Title>Despesas por Categoria</Card.Title>
                            <Pie
                                data={{
                                    labels: dados.despesasPorCategoria.map(item => item.categoria),
                                    datasets: [{
                                        data: dados.despesasPorCategoria.map(item => item.valor),
                                        backgroundColor: [
                                            '#dc3545',
                                            '#fd7e14',
                                            '#ffc107',
                                            '#d63384',
                                            '#6f42c1'
                                        ]
                                    }]
                                }}
                                options={{
                                    responsive: true,
                                    plugins: {
                                        legend: {
                                            position: 'right'
                                        }
                                    }
                                }}
                            />
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            <Card>
                <Card.Body>
                    <Card.Title>Evolução Mensal</Card.Title>
                    <Line
                        data={{
                            labels: dados.evolucaoMensal.labels,
                            datasets: [
                                {
                                    label: 'Receitas',
                                    data: dados.evolucaoMensal.receitas,
                                    borderColor: '#28a745',
                                    backgroundColor: 'rgba(40, 167, 69, 0.1)',
                                    tension: 0.4
                                },
                                {
                                    label: 'Despesas',
                                    data: dados.evolucaoMensal.despesas,
                                    borderColor: '#dc3545',
                                    backgroundColor: 'rgba(220, 53, 69, 0.1)',
                                    tension: 0.4
                                }
                            ]
                        }}
                        options={{
                            responsive: true,
                            plugins: {
                                legend: {
                                    position: 'top'
                                }
                            },
                            scales: {
                                y: {
                                    beginAtZero: true
                                }
                            }
                        }}
                    />
                </Card.Body>
            </Card>
        </div>
    )
} 