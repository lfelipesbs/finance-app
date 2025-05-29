import React, { useState, useEffect } from 'react'
import { Table, Button, Form, Card } from 'react-bootstrap'
import { Link } from 'react-router-dom'
import axios from 'axios'
import './CategoriaList.css'

export default function CategoriaList() {
    const [categorias, setCategorias] = useState([])
    const [novaCategoria, setNovaCategoria] = useState({
        nome: '',
        tipo: 'DESPESA'
    })

    useEffect(() => {
        carregarCategorias()
    }, [])

    const carregarCategorias = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/categorias')
            setCategorias(response.data)
        } catch (error) {
            console.error('Erro ao carregar categorias:', error)
        }
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        try {
            await axios.post('http://localhost:8080/api/categorias', novaCategoria)
            setNovaCategoria({ nome: '', tipo: 'DESPESA' })
            carregarCategorias()
        } catch (error) {
            console.error('Erro ao criar categoria:', error)
        }
    }

    const handleChange = (e) => {
        const { name, value } = e.target
        setNovaCategoria(prev => ({
            ...prev,
            [name]: value
        }))
    }

    const handleExcluirCategoria = async (categoria) => {
        try {
            const response = await axios.get(`http://localhost:8080/api/transacoes/categoria/${categoria.id}`)
            if (response.data.length > 0) {
                alert('Não é possível excluir esta categoria pois existem transações vinculadas a ela.')
                return
            }

            if (window.confirm('Tem certeza que deseja excluir esta categoria? Excluirá todas as transações vinculadas a ela.')) {
                await axios.delete(`http://localhost:8080/api/categorias/${categoria.id}`)
                carregarCategorias()
            }
        } catch (error) {
            if (error.response?.status === 409) {
                alert('Não é possível excluir esta categoria pois existem transações vinculadas a ela.')
            } else {
                console.error('Erro ao excluir categoria:', error)
                alert('Erro ao excluir categoria. Tente novamente.')
            }
        }
    }

    return (
        <div className="container mt-4">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2>Categorias</h2>
            </div>

            <Card className="mb-4">
                <Card.Body>
                    <Form onSubmit={handleSubmit}>
                        <div className="d-flex gap-3">
                            <Form.Group className="flex-grow-1">
                                <Form.Label>Nome da Categoria</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="nome"
                                    value={novaCategoria.nome}
                                    onChange={handleChange}
                                    placeholder="Digite o nome da categoria"
                                    required
                                />
                            </Form.Group>
                            <Form.Group className="flex-grow-1">
                                <Form.Label>Tipo</Form.Label>
                                <Form.Select
                                    name="tipo"
                                    value={novaCategoria.tipo}
                                    onChange={handleChange}
                                    required
                                >
                                    <option value="RECEITA">Receita</option>
                                    <option value="DESPESA">Despesa</option>
                                </Form.Select>
                            </Form.Group>
                            <div className="d-flex align-items-end">
                                <Button type="submit" variant="primary">
                                    Adicionar Categoria
                                </Button>
                            </div>
                        </div>
                    </Form>
                </Card.Body>
            </Card>

            <Table striped hover>
                <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Tipo</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {categorias.map(categoria => (
                        <tr key={categoria.id}>
                            <td>{categoria.nome}</td>
                            <td>
                                <span className={categoria.tipo === 'RECEITA' ? 'text-success' : 'text-danger'}>
                                    {categoria.tipo === 'RECEITA' ? 'Receita' : 'Despesa'}
                                </span>
                            </td>
                            <td>
                                <div className="d-flex gap-2">
                                    <Button
                                        variant="outline-info"
                                        size="sm"
                                        className="btn-icon"
                                        as={Link}
                                        to={`/categorias/editar/${categoria.id}`}
                                    >
                                        ✏️
                                    </Button>
                                    <Button
                                        variant="outline-danger"
                                        size="sm"
                                        className="btn-icon"
                                        onClick={() => handleExcluirCategoria(categoria)}
                                    >
                                        🗑️
                                    </Button>
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>
        </div>
    )
} 