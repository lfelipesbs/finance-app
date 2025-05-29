import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Container } from 'react-bootstrap';
import Sidebar from './components/layout/Sidebar';
import Header from './components/layout/Header';
import Dashboard from './components/Dashboard';
import TransacaoList from './components/TransacaoList';
import Categorias from './components/Categorias';
import Relatorios from './components/Relatorios';
import './App.css';

function App() {
    return (
        <Router>
            <div className="app">
                <Sidebar />
                <div className="main-content">
                    <Header />
                    <Container fluid className="content">
                        <Routes>
                            <Route path="/" element={<Dashboard />} />
                            <Route path="/transacoes" element={<TransacaoList />} />
                            <Route path="/categorias" element={<Categorias />} />
                            <Route path="/relatorios" element={<Relatorios />} />
                        </Routes>
                    </Container>
                </div>
            </div>
        </Router>
    );
}

export default App;
