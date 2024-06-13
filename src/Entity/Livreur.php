<?php

namespace App\Entity;

use App\Repository\LivreurRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: LivreurRepository::class)]
class Livreur
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer')]
    private $id_livreur;

    #[ORM\Column(type: 'string', length: 255)]
    private $nom_livreur;

    #[ORM\Column(type: 'string', length: 255)]
    private $prenom_livreur;

    #[ORM\Column(type: 'string', length: 255)]
    private $adresse;

    #[ORM\Column(type: 'string', length: 20)]
    private $telephone;

    #[ORM\Column(type: 'string', length: 20)]
    private $matricule;

    // Define getters and setters for properties

    public function getIdLivreur(): ?int
    {
        return $this->id_livreur;
    }

    public function getNomLivreur(): string
    {
        return $this->nom_livreur;
    }

    public function setNomLivreur(string $nom_livreur): self
    {
        $this->nom_livreur = $nom_livreur;
        return $this;
    }

    // Repeat for other properties

    public function getMatricule(): string
    {
        return $this->matricule;
    }

    public function setMatricule(string $matricule): self
    {
        $this->matricule = $matricule;
        return $this;
    }

    // Other methods as needed
}
