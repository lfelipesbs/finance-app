import React, { useState } from 'react'
import api from '../services/api'
import './Form.css'

export default function InsertTransacaoForm({ onSaved }) {
    const [form, setForm] = useState({
        data: '',
        descricao: '',
        valor: '',
        tipo: 'receita',
        categoriaId: 1
    })

    const submit = e => {
        e.preventDefault()
        api.post('/transacoes', form).then(() => {
            onSaved()
            setForm({ data:'', descricao:'', valor:'', tipo:'receita', categoriaId:1 })
        })
    }

    return (
        <form className="form-insert" onSubmit={submit}>
            <input
                type="date"
                value={form.data}
                onChange={e => setForm({ ...form, data: e.target.value })}
                required
            />
            <input
                type="text"
                placeholder="Descrição"
                value={form.descricao}
                onChange={e => setForm({ ...form, descricao: e.target.value })}
                required 
            />
            <input
                type="number"
                step="0.01"
                placeholder="Valor"
                value={form.valor}
                onChange={e => setForm({ ...form, valor: e.target.value })}
                required
            />
            <select
                value={form.tipo}
                onChange={e => setForm({ ...form, tipo: e.target.value })}
            >
                <option value="receita">Receita</option>
                <option value="despesa">Despesa</option>
            </select>
            <button type="submit" className="btn save">
                ➕ Adicionar
            </button>
        </form>
    )
}
