<?php

namespace App\Entity;

use App\Repository\CommandeRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: CommandeRepository::class)]
class Commande
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer')]
    private $id_commande;

    #[ORM\Column(type: 'string', length: 255)]
    private $name_commande;

    #[ORM\Column(type: 'date')]
    private $date_commande;

    #[ORM\Column(type: 'float')]
    private $prix_commande;

    #[ORM\Column(type: 'string', length: 255)]
    private $payment;

    #[ORM\Column(type: 'integer')]
    private $id_oeuvre;

    #[ORM\Column(type: 'integer')]
    private $id_user;

    // Define getters and setters for properties

    public function getIdCommande(): ?int
    {
        return $this->id_commande;
    }

    public function getNameCommande(): string
    {
        return $this->name_commande;
    }

    public function setNameCommande(string $name_commande): self
    {
        $this->name_commande = $name_commande;
        return $this;
    }

    // Other getters and setters for other properties
}
