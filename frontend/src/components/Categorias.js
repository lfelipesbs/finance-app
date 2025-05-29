import React, { useState, useEffect } from 'react'
import { Table, Button, Form, Card, Modal } from 'react-bootstrap'
import axios from 'axios'
import './Categorias.css'

export default function Categorias() {
    const [categorias, setCategorias] = useState([])
    const [showModal, setShowModal] = useState(false)
    const [categoriaEditando, setCategoriaEditando] = useState(null)
    const [formData, setFormData] = useState({ nome: '' })

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
            if (categoriaEditando) {
                await axios.put(`http://localhost:8080/api/categorias/${categoriaEditando.id}`, formData)
            } else {
                await axios.post('http://localhost:8080/api/categorias', formData)
            }
            setShowModal(false)
            setCategoriaEditando(null)
            setFormData({ nome: '' })
            carregarCategorias()
        } catch (error) {
            console.error('Erro ao salvar categoria:', error)
        }
    }

    const handleEdit = (categoria) => {
        setCategoriaEditando(categoria)
        setFormData({ nome: categoria.nome })
        setShowModal(true)
    }

    const handleDelete = async (id) => {
        if (window.confirm('Tem certeza que deseja excluir esta categoria? Excluirá todas as transações vinculadas a ela.')) {
            try {
                await axios.delete(`http://localhost:8080/api/categorias/${id}`)
                carregarCategorias()
            } catch (error) {
                console.error('Erro ao excluir categoria:', error)
            }
        }
    }

    const handleCloseModal = () => {
        setShowModal(false)
        setCategoriaEditando(null)
        setFormData({ nome: '' })
    }

    return (
        <div className="categorias">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2>Categorias</h2>
                <Button variant="primary" onClick={() => setShowModal(true)}>
                    <i className="fas fa-plus"></i> Nova Categoria
                </Button>
            </div>

            <Card>
                <Card.Body>
                    <Table striped hover>
                        <thead>
                            <tr>
                                <th>Nome</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            {categorias.map(categoria => (
                                <tr key={categoria.id}>
                                    <td>{categoria.nome}</td>
                                    <td>
                                        <Button
                                            variant="primary"
                                            size="sm"
                                            className="me-2"
                                            onClick={() => handleEdit(categoria)}
                                        >
                                            <i className="fas fa-edit"></i>
                                        </Button>
                                        <Button
                                            variant="danger"
                                            size="sm"
                                            onClick={() => handleDelete(categoria.id)}
                                        >
                                            <i className="fas fa-trash"></i>
                                        </Button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </Table>
                </Card.Body>
            </Card>

            <Modal show={showModal} onHide={handleCloseModal}>
                <Modal.Header closeButton>
                    <Modal.Title>
                        {categoriaEditando ? 'Editar Categoria' : 'Nova Categoria'}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group className="mb-3">
                            <Form.Label>Nome</Form.Label>
                            <Form.Control
                                type="text"
                                value={formData.nome}
                                onChange={(e) => setFormData({ nome: e.target.value })}
                                required
                            />
                        </Form.Group>
                        <div className="d-flex justify-content-end gap-2">
                            <Button variant="secondary" onClick={handleCloseModal}>
                                Cancelar
                            </Button>
                            <Button variant="primary" type="submit">
                                Salvar
                            </Button>
                        </div>
                    </Form>
                </Modal.Body>
            </Modal>
        </div>
    )
} 