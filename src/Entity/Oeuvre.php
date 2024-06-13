<?php

namespace App\Entity;

use App\Repository\OeuvreRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: OeuvreRepository::class)]
class Oeuvre
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer')]
    private $id_oeuvre;

    #[ORM\Column(type: 'string', length: 255)]
    private $titre;

    #[ORM\Column(type: 'text')]
    private $description;

    #[ORM\Column(type: 'decimal', precision: 10, scale: 2)]
    private $prix;

    #[ORM\Column(type: 'datetime')]
    private $date;

    #[ORM\Column(type: 'string', length: 255)]
    private $status;

    #[ORM\Column(type: 'string', length: 255)]
    private $lienImg;

    #[ORM\Column(name: 'idUser', type: 'integer')]
    private $idUser; // Just a regular attribute

    #[ORM\ManyToOne(targetEntity: Category::class)]
    #[ORM\JoinColumn(name: 'id_cat', referencedColumnName: 'id_cat')]
    private $id_cat;
  
    // Define getters and setters for properties

    public function getIdOeuvre(): ?int
    {
        return $this->id_oeuvre;
    }

    public function getTitre(): string
    {
        return $this->titre;
    }

    public function setTitre(string $titre): self
    {
        $this->titre = $titre;
        return $this;
    }

    // Implement getters and setters for other properties

    public function getIdCat(): ?Category
    {
        return $this->id_cat;
    }

    public function setIdCat(?Category $id_cat): self
    {
        $this->id_cat = $id_cat;
        return $this;
    }

  
    // Other methods as needed
}
