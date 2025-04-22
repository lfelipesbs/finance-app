import React, { useEffect, useState } from 'react'
import api from '../services/api'
import './TransacaoList.css'

export default function TransacaoList({ refreshTrigger, onEdit }) {
    const [transacoes, setTransacoes] = useState([])

    useEffect(() => {
        api.get('/transacoes').then(res => setTransacoes(res.data))
    }, [refreshTrigger]) 

    const remove = id => {
        if (!window.confirm('Confirma exclusão?')) return
            api.delete(`/transacoes/${id}`).then(() =>
            setTransacoes(ts => ts.filter(t => t.id !== id))
        )
    }

    return (
        <table className="transacao-table">
            <thead>
                <tr>
                <th>ID</th>
                <th>Data</th>
                <th>Descrição</th>
                <th>Tipo</th>
                <th>Valor</th>
                <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                {transacoes.map(t => (
                    <tr key={t.id} className={t.tipo === 'receita' ? 'row-receita' : 'row-despesa'}>
                        <td>{t.id}</td>
                        <td>{t.data}</td>
                        <td>{t.descricao}</td>
                        <td>
                            <span className={`badge badge-${t.tipo}`}>
                                {t.tipo === 'receita' ? '💰 Receita' : '🔻 Despesa'}
                            </span>
                        </td>
                        <td>R$ {t.valor.toFixed(2)}</td>
                        <td>
                        <button className="btn edit" onClick={() => onEdit(t)}>
                            ✏️
                        </button>
                        <button className="btn delete" onClick={() => remove(t.id)}>
                            🗑️
                        </button>
                        </td>
                    </tr>
                ))}
            </tbody>
        </table>
    )
}
