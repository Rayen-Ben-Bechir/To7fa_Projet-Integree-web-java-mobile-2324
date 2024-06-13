<?php

namespace App\Entity;

use App\Repository\EvenementRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: EvenementRepository::class)]
class Evenement
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer')]
    private $id_event;

    #[ORM\Column(type: 'string', length: 255)]
    private $nom_event;

    #[ORM\Column(type: 'text')]
    private $description_event;

    #[ORM\Column(type: 'string', length: 255)]
    private $lieu_event;

    #[ORM\Column(type: 'string', length: 255)]
    private $type_event;

    #[ORM\Column(type: 'datetime')]
    private $date_debut_event;

    #[ORM\Column(type: 'datetime')]
    private $date_fin_event;

    #[ORM\Column(type: 'integer')]
    private $capacite_event;

    #[ORM\Column(type: 'string', length: 255)]
    private $image_event;

    #[ORM\Column(type: 'decimal', precision: 10, scale: 2)]
    private $prix_event;

    // Define getters and setters for properties

    public function getIdEvent(): ?int
    {
        return $this->id_event;
    }

    public function getNomEvent(): string
    {
        return $this->nom_event;
    }

    public function setNomEvent(string $nom_event): self
    {
        $this->nom_event = $nom_event;
        return $this;
    }

    // Other methods as needed
}
