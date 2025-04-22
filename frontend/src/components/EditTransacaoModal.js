import React, { useState } from 'react'
import api from '../services/api'
import './Modal.css'

export default function EditTransacaoModal({ transacao, onCancel, onSaved }) {
    const [form, setForm] = useState({ ...transacao })

    const submit = e => {
        e.preventDefault()
        api.put(`/transacoes/${transacao.id}`, form).then(onSaved)
    }

    return (
        <div className="modal-backdrop" onClick={onCancel}>
            <div className="modal-content" onClick={e => e.stopPropagation()}>
                <h2>Editar Transação #{transacao.id}</h2>
                <form onSubmit={submit}>
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
                    <div className="modal-actions">
                        <button type="button" className="btn cancel" onClick={onCancel}>
                            ❌ Cancelar
                        </button>
                        <button type="submit" className="btn save">
                            💾 Salvar
                        </button>
                    </div>
                </form>
            </div>
        </div>
    )
}
