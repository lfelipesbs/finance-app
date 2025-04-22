import React, { useState } from 'react'
import InsertTransacaoForm from './components/InsertTransacaoForm'
import TransacaoList from './components/TransacaoList'
import EditTransacaoModal from './components/EditTransacaoModal'
import './App.css'

export default function App() {
    const [editing, setEditing] = useState(null)
    const [refreshTrigger, setRefreshTrigger] = useState(0)

    const reload = () => setRefreshTrigger(t => t + 1)

    return (
        <div className="container">
            <h1>Gestão Financeira</h1>
            <InsertTransacaoForm onSaved={reload} />
            <TransacaoList
                refreshTrigger={refreshTrigger}
                onEdit={t => setEditing(t)}
            />
            {editing && (
                <EditTransacaoModal
                    transacao={editing}
                    onCancel={() => setEditing(null)}
                    onSaved={() => {
                        setEditing(null)
                        reload()
                    }}
                />
            )}
        </div>
    )
}
