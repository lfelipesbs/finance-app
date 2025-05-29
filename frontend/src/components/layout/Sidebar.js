import React from 'react'
import { NavLink } from 'react-router-dom'
import './Sidebar.css'

export default function Sidebar() {
    return (
        <div className="sidebar">
            <div className="sidebar-header">
                <h2>Finance App</h2>
            </div>
            <nav className="sidebar-nav">
                <NavLink to="/" className={({ isActive }) => isActive ? 'nav-item active' : 'nav-item'}>
                    <i className="fas fa-chart-line"></i>
                    Dashboard
                </NavLink>
                <NavLink to="/transacoes" className={({ isActive }) => isActive ? 'nav-item active' : 'nav-item'}>
                    <i className="fas fa-exchange-alt"></i>
                    Transações
                </NavLink>
                <NavLink to="/categorias" className={({ isActive }) => isActive ? 'nav-item active' : 'nav-item'}>
                    <i className="fas fa-tags"></i>
                    Categorias
                </NavLink>
                <NavLink to="/relatorios" className={({ isActive }) => isActive ? 'nav-item active' : 'nav-item'}>
                    <i className="fas fa-file-alt"></i>
                    Relatórios
                </NavLink>
            </nav>
        </div>
    )
} 