module R where

open import Data.List
open import Data.List.Properties
open import Relation.Binary.PropositionalEquality
open ≡-Reasoning

-- Inefficient reverse and its correctness

rev1 : {A : Set} → List A → List A
rev1 [] = []
rev1 (x ∷ xs) = rev1 xs ++ (x ∷ [])

test1 : rev1 (1 ∷ 2 ∷ 3 ∷ 4 ∷ 5 ∷ []) ≡ 5 ∷ 4 ∷ 3 ∷ 2 ∷ 1 ∷ []
test1 = refl 

rev1++ : {A : Set} → (xs : List A) → (ys : List A) →
         rev1 (xs ++ ys) ≡ rev1 ys ++ rev1 xs
rev1++ [] ys = {!!}
rev1++ (x ∷ xs) ys =
  rev1 ((x ∷ xs) ++ ys)
    ≡⟨ {!!} ⟩ 
  rev1 ys ++ rev1 (x ∷ xs) ∎

rev1rev1 : {A : Set} → (xs : List A) → rev1 (rev1 xs) ≡ xs
rev1rev1 [] = {!!} 
rev1rev1 (x ∷ xs) =
  rev1 (rev1 (x ∷ xs))
    ≡⟨ {!!} ⟩
  x ∷ xs ∎

--
-- Efficient reverse and its correctness

rev2H : {A : Set} → List A → List A → List A
rev2H [] ys = ys
rev2H (x ∷ xs) ys = rev2H xs (x ∷ ys)

rev2 : {A : Set} → List A → List A
rev2 xs = rev2H xs [] 

test2 : rev2 (1 ∷ 2 ∷ 3 ∷ 4 ∷ 5 ∷ []) ≡ 5 ∷ 4 ∷ 3 ∷ 2 ∷ 1 ∷ []
test2 = refl

-- Connect to rev1

rev2H1++ : {A : Set} → (xs : List A) → (ys : List A) →
           rev2H xs ys ≡ rev1 xs ++ ys
rev2H1++ [] ys = {!!} 
rev2H1++ (x ∷ xs) ys =
  rev2H (x ∷ xs) ys
    ≡⟨ {!!} ⟩ 
  rev1 (x ∷ xs) ++ ys ∎

rev2rev2 : {A : Set} → (xs : List A) → rev2 (rev2 xs) ≡ xs
rev2rev2 xs =
  rev2 (rev2 xs)
    ≡⟨ {!!} ⟩
  xs ∎
 
-- Equivalence

rev1≡rev2 : {A : Set} → (xs : List A) → rev2 xs ≡ rev1 xs
rev1≡rev2 xs =
  rev2 xs
    ≡⟨ {!!} ⟩
  rev1 xs ∎ 

--
