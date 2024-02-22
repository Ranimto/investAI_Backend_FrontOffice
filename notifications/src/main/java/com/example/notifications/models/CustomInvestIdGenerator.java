package com.example.notifications.models;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class CustomInvestIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        // Logique de génération de l'identifiant personnalisé pour InvestId

        if (obj instanceof Investment) {
            Investment investment = (Investment) obj;
            Long investorId = investment.getUser().getId();
            Long companyId = investment.getCompany().getId();

            // Créer un identifiant unique en combinant l'ID de l'investisseur et l'ID de l'entreprise
            InvestId id = new InvestId(investorId, companyId);
            return id;

        } else {
            throw new IllegalArgumentException("Objet non valide pour la génération d'identifiant");
        }
    }
}
