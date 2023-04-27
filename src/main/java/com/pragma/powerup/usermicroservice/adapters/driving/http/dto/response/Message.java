package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response;

    public class Message {
    
        String mensaje;
    
        public Message(String mensaje) {
            this.mensaje = mensaje;
        }
    
        public String getMensaje() {
            return mensaje;
        }
    
        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    
    }
