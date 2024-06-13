<?php

namespace App\Entity;

use App\Repository\MuseeRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: MuseeRepository::class)]
class Musee
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer')]
    private $id_musee;

    #[ORM\Column(type: 'string', length: 255)]
    private $nom_musee;

    #[ORM\Column(type: 'string', length: 255)]
    private $adresse;

    #[ORM\Column(type: 'string', length: 255)]
    private $ville;

    #[ORM\Column(type: 'integer')]
    private $nbr_tickets_disponibles;

    #[ORM\Column(type: 'text')]
    private $description;

    #[ORM\Column(type: 'string', length: 255)]
    private $image_musee;

    // Define getters and setters for properties

    public function getIdMusee(): ?int
    {
        return $this->id_musee;
    }

    public function getNomMusee(): string
    {
        return $this->nom_musee;
    }

    public function setNomMusee(string $nom_musee): self
    {
        $this->nom_musee = $nom_musee;
        return $this;
    }

    // Add getters and setters for other properties as needed
}
