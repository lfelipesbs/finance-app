import React, { useState, useEffect, useCallback } from 'react'
import { Table, Button, Form, Row, Col, Card, Modal } from 'react-bootstrap'
import axios from 'axios'
import { format } from 'date-fns'
import { ptBR } from 'date-fns/locale'
import { formatMoney } from '../utils/format'
import './TransacaoList.css'

export default function TransacaoList() {
    const [transacoes, setTransacoes] = useState([])
    const [categorias, setCategorias] = useState([])
    const [filtros, setFiltros] = useState({
        inicio: '',
        fim: '',
        categoriaId: '',
        tipo: ''
    })
    const [ordenacao, setOrdenacao] = useState({
        coluna: 'dataTransacao',
        direcao: 'DESC'
    })
    const [showModal, setShowModal] = useState(false)
    const [showEditModal, setShowEditModal] = useState(false)
    const [novaTransacao, setNovaTransacao] = useState({
        descricao: '',
        valor: '',
        dataTransacao: format(new Date(), 'yyyy-MM-dd'),
        tipo: 'DESPESA',
        categoriaId: ''
    })
    const [transacaoEditando, setTransacaoEditando] = useState(null)

    const carregarTransacoes = useCallback(async () => {
        try {
            const params = {
                orderBy: ordenacao.coluna,
                direction: ordenacao.direcao
            }

            if (filtros.inicio) {
                params.inicio = filtros.inicio
            }
            if (filtros.fim) {
                params.fim = filtros.fim
            }
            if (filtros.tipo) {
                params.tipo = filtros.tipo
            }
            if (filtros.categoriaId) {
                params.categoriaId = filtros.categoriaId
            }

            const response = await axios.get('http://localhost:8080/api/transacoes', { params })
            setTransacoes(response.data)
        } catch (error) {
            console.error('Erro ao carregar transações:', error)
        }
    }, [filtros, ordenacao])

    const carregarCategorias = useCallback(async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/categorias')
            setCategorias(response.data)
        } catch (error) {
            console.error('Erro ao carregar categorias:', error)
        }
    }, []);

    useEffect(() => {
        carregarCategorias()
        carregarTransacoes()
    }, [filtros, ordenacao, carregarCategorias, carregarTransacoes])

    const handleFiltroChange = (e) => {
        const { name, value } = e.target
        setFiltros(prev => ({
            ...prev,
            [name]: value
        }))
    }

    const limparFiltros = () => {
        setFiltros({
            inicio: '',
            fim: '',
            categoriaId: '',
            tipo: ''
        })
    }

    const handleOrdenacao = (coluna) => {
        setOrdenacao(prev => ({
            coluna,
            direcao: prev.coluna === coluna && prev.direcao === 'DESC' ? 'ASC' : 'DESC'
        }))
    }

    const handleNovaTransacaoChange = (e) => {
        const { name, value } = e.target
        setNovaTransacao(prev => ({
            ...prev,
            [name]: value
        }))
    }

    const handleEditarTransacaoChange = (e) => {
        const { name, value } = e.target
        setTransacaoEditando(prev => ({
            ...prev,
            [name]: value
        }))
    }

    const handleNovaTransacaoSubmit = async (e) => {
        e.preventDefault()
        try {
            const dataParaEnviar = {
                ...novaTransacao,
                dataTransacao: novaTransacao.dataTransacao
            }
            await axios.post('http://localhost:8080/api/transacoes', dataParaEnviar)
            setNovaTransacao({
                descricao: '',
                valor: '',
                dataTransacao: format(new Date(), 'yyyy-MM-dd'),
                tipo: 'DESPESA',
                categoriaId: ''
            })
            setShowModal(false)
            carregarTransacoes()
        } catch (error) {
            console.error('Erro ao criar transação:', error)
        }
    }

    const handleEditarTransacaoSubmit = async (e) => {
        e.preventDefault()
        try {
            const dataParaEnviar = {
                ...transacaoEditando,
                dataTransacao: transacaoEditando.dataTransacao
            }
            await axios.put(`http://localhost:8080/api/transacoes/${transacaoEditando.id}`, dataParaEnviar)
            setShowEditModal(false)
            setTransacaoEditando(null)
            carregarTransacoes()
        } catch (error) {
            console.error('Erro ao editar transação:', error)
        }
    }

    const handleEditarClick = (transacao) => {
        const data = new Date(transacao.dataTransacao)
        data.setDate(data.getDate() + 1)
        setTransacaoEditando({
            ...transacao,
            dataTransacao: format(data, 'yyyy-MM-dd')
        })
        setShowEditModal(true)
    }

    const renderOrdenacaoIcon = (coluna) => {
        if (ordenacao.coluna !== coluna) return '↕️'
        return ordenacao.direcao === 'ASC' ? '⬆️' : '⬇️'
    }

    return (
        <div className="container-fluid px-4">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2>Transações</h2>
                <Button variant="primary" onClick={() => setShowModal(true)}>
                    <i className="fas fa-plus"></i> Nova Transação
                </Button>
            </div>

            <Card className="mb-4">
                <Card.Body>
                    <Row className="justify-content-between">
                        <Col md={3}>
                            <Form.Group>
                                <Form.Label>Data Início</Form.Label>
                                <Form.Control
                                    type="date"
                                    name="inicio"
                                    value={filtros.inicio}
                                    onChange={handleFiltroChange}
                                />
                            </Form.Group>
                        </Col>
                        <Col md={3}>
                            <Form.Group>
                                <Form.Label>Data Fim</Form.Label>
                                <Form.Control
                                    type="date"
                                    name="fim"
                                    value={filtros.fim}
                                    onChange={handleFiltroChange}
                                />
                            </Form.Group>
                        </Col>
                        <Col md={2}>
                            <Form.Group>
                                <Form.Label>Categoria</Form.Label>
                                <Form.Select
                                    name="categoriaId"
                                    value={filtros.categoriaId}
                                    onChange={handleFiltroChange}
                                >
                                    <option value="">Todas</option>
                                    {categorias.map(categoria => (
                                        <option key={categoria.id} value={categoria.id}>
                                            {categoria.nome}
                                        </option>
                                    ))}
                                </Form.Select>
                            </Form.Group>
                        </Col>
                        <Col md={2}>
                            <Form.Group>
                                <Form.Label>Tipo</Form.Label>
                                <Form.Select
                                    name="tipo"
                                    value={filtros.tipo}
                                    onChange={handleFiltroChange}
                                >
                                    <option value="">Todos</option>
                                    <option value="RECEITA">Receita</option>
                                    <option value="DESPESA">Despesa</option>
                                </Form.Select>
                            </Form.Group>
                        </Col>
                        <Col md={1} className="d-flex align-items-end">
                            <Button 
                                variant="outline-secondary" 
                                onClick={limparFiltros}
                                className="w-100 btn-icon"
                                title="Limpar Filtros"
                            >
                                ❌
                            </Button>
                        </Col>
                    </Row>
                </Card.Body>
            </Card>

            <div className="table-responsive">
                <Table striped hover>
                    <thead>
                        <tr>
                            <th onClick={() => handleOrdenacao('dataTransacao')} style={{ cursor: 'pointer' }}>
                                Data {renderOrdenacaoIcon('dataTransacao')}
                            </th>
                            <th onClick={() => handleOrdenacao('descricao')} style={{ cursor: 'pointer' }}>
                                Descrição {renderOrdenacaoIcon('descricao')}
                            </th>
                            <th onClick={() => handleOrdenacao('categoria')} style={{ cursor: 'pointer' }}>
                                Categoria {renderOrdenacaoIcon('categoria')}
                            </th>
                            <th onClick={() => handleOrdenacao('valor')} style={{ cursor: 'pointer' }}>
                                Valor {renderOrdenacaoIcon('valor')}
                            </th>
                            <th onClick={() => handleOrdenacao('tipo')} style={{ cursor: 'pointer' }}>
                                Tipo {renderOrdenacaoIcon('tipo')}
                            </th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {transacoes.map(transacao => {
                            const data = new Date(transacao.dataTransacao)
                            data.setDate(data.getDate() + 1)
                            return (
                                <tr key={transacao.id}>
                                    <td>{format(data, 'dd/MM/yyyy', { locale: ptBR })}</td>
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
                                    <td>
                                        <div className="d-flex gap-2">
                                            <Button
                                                variant="outline-info"
                                                size="sm"
                                                className="btn-icon"
                                                onClick={() => handleEditarClick(transacao)}
                                            >
                                                ✏️
                                            </Button>
                                            <Button
                                                variant="outline-danger"
                                                size="sm"
                                                className="btn-icon"
                                                onClick={() => {
                                                    if (window.confirm('Tem certeza que deseja excluir esta transação?')) {
                                                        axios.delete(`http://localhost:8080/api/transacoes/${transacao.id}`)
                                                            .then(() => carregarTransacoes())
                                                            .catch(error => console.error('Erro ao excluir transação:', error))
                                                    }
                                                }}
                                            >
                                                🗑️
                                            </Button>
                                        </div>
                                    </td>
                                </tr>
                            )
                        })}
                    </tbody>
                </Table>
            </div>

            <Modal show={showModal} onHide={() => setShowModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Nova Transação</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handleNovaTransacaoSubmit}>
                        <Form.Group className="mb-3">
                            <Form.Label>Descrição</Form.Label>
                            <Form.Control
                                type="text"
                                name="descricao"
                                value={novaTransacao.descricao}
                                onChange={handleNovaTransacaoChange}
                                placeholder="Digite a descrição"
                                required
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Valor</Form.Label>
                            <Form.Control
                                type="number"
                                step="0.01"
                                name="valor"
                                value={novaTransacao.valor}
                                onChange={handleNovaTransacaoChange}
                                placeholder="Digite o valor"
                                required
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Data</Form.Label>
                            <Form.Control
                                type="date"
                                name="dataTransacao"
                                value={novaTransacao.dataTransacao}
                                onChange={handleNovaTransacaoChange}
                                required
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Tipo</Form.Label>
                            <Form.Select
                                name="tipo"
                                value={novaTransacao.tipo}
                                onChange={handleNovaTransacaoChange}
                                required
                            >
                                <option value="RECEITA">Receita</option>
                                <option value="DESPESA">Despesa</option>
                            </Form.Select>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Categoria</Form.Label>
                            <Form.Select
                                name="categoriaId"
                                value={novaTransacao.categoriaId}
                                onChange={handleNovaTransacaoChange}
                                required
                            >
                                <option value="">Selecione uma categoria</option>
                                {categorias.map(categoria => (
                                    <option key={categoria.id} value={categoria.id}>
                                        {categoria.nome}
                                    </option>
                                ))}
                            </Form.Select>
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowModal(false)}>
                        Cancelar
                    </Button>
                    <Button variant="primary" onClick={handleNovaTransacaoSubmit}>
                        Adicionar
                    </Button>
                </Modal.Footer>
            </Modal>

            <Modal show={showEditModal} onHide={() => setShowEditModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Editar Transação</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handleEditarTransacaoSubmit}>
                        <Form.Group className="mb-3">
                            <Form.Label>Descrição</Form.Label>
                            <Form.Control
                                type="text"
                                name="descricao"
                                value={transacaoEditando?.descricao || ''}
                                onChange={handleEditarTransacaoChange}
                                placeholder="Digite a descrição"
                                required
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Valor</Form.Label>
                            <Form.Control
                                type="number"
                                step="0.01"
                                name="valor"
                                value={transacaoEditando?.valor || ''}
                                onChange={handleEditarTransacaoChange}
                                placeholder="Digite o valor"
                                required
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Data</Form.Label>
                            <Form.Control
                                type="date"
                                name="dataTransacao"
                                value={transacaoEditando?.dataTransacao || ''}
                                onChange={handleEditarTransacaoChange}
                                required
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Tipo</Form.Label>
                            <Form.Select
                                name="tipo"
                                value={transacaoEditando?.tipo || ''}
                                onChange={handleEditarTransacaoChange}
                                required
                            >
                                <option value="RECEITA">Receita</option>
                                <option value="DESPESA">Despesa</option>
                            </Form.Select>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Categoria</Form.Label>
                            <Form.Select
                                name="categoriaId"
                                value={transacaoEditando?.categoriaId || ''}
                                onChange={handleEditarTransacaoChange}
                                required
                            >
                                <option value="">Selecione uma categoria</option>
                                {categorias.map(categoria => (
                                    <option key={categoria.id} value={categoria.id}>
                                        {categoria.nome}
                                    </option>
                                ))}
                            </Form.Select>
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowEditModal(false)}>
                        Cancelar
                    </Button>
                    <Button variant="primary" onClick={handleEditarTransacaoSubmit}>
                        Salvar
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    )
}
