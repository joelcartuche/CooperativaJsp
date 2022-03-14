/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cooperativa;

import Controladores.UsuarioJpaController;
import Modelos.Cuenta;
import Modelos.Usuario;

/**
 *
 * @author joelc
 */
public class TestContacto {
    public static void main(String[] args) {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("usuarioNombre");
        usuario.setApellidoUsuario("usuarioApellido");
        usuario.setCedulaUsuario("11111");
        usuario.setTelefonoUsuario("2222");
        UsuarioJpaController usuarioJpaController = new UsuarioJpaController();
        usuarioJpaController.create(usuario);
        
        
    }
}
