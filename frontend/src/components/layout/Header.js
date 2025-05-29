import React from 'react'
import './Header.css'

export default function Header() {
    return (
        <header className="header">
            <div className="header-content">
                <h1>Finance App</h1>
                <div className="user-info">
                    <i className="fas fa-user-circle"></i>
                    <span>Administrador</span>
                </div>
            </div>
        </header>
    )
}