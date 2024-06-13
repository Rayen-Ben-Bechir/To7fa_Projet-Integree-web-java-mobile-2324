<?php

namespace App\Entity;

use App\Repository\LivraisonRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: LivraisonRepository::class)]
class Livraison
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer')]
    private $id_livraison;

    #[ORM\Column(type: 'datetime')]
    private $date_livraison;

    #[ORM\Column(type: 'string', length: 255)]
    private $adresse_livraison;

    #[ORM\Column(type: 'string', length: 255)]
    private $status_livraison;

    #[ORM\Column(type: 'decimal', precision: 10, scale: 2)]
    private $frais_livraison;

    #[ORM\Column(type: 'integer')]
    private $id_commande;

    #[ORM\Column(type: 'integer')]
    private $id_livreur;

    // Define getters and setters for properties

    public function getIdLivraison(): ?int
    {
        return $this->id_livraison;
    }

    public function getDateLivraison(): \DateTimeInterface
    {
        return $this->date_livraison;
    }

    public function setDateLivraison(\DateTimeInterface $date_livraison): self
    {
        $this->date_livraison = $date_livraison;
        return $this;
    }

    // Add getters and setters for other properties

    public function getAdresseLivraison(): string
    {
        return $this->adresse_livraison;
    }

    public function setAdresseLivraison(string $adresse_livraison): self
    {
        $this->adresse_livraison = $adresse_livraison;
        return $this;
    }

    // Similar methods for other properties

    // Other methods as needed
}
